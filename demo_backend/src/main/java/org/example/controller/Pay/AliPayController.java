package org.example.controller.Pay;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mmj
 * @Description 使用通用版的SDK
 * @create 2024-06-23 10:32
 */
@RestController
@RequestMapping("alipay")
public class AliPayController {
    /**
     * 当面付 统一创建交易生成二维码接口
     * @param orderNo
     * @return
     */
    @RequestMapping("precreate")
    public String precreate(String orderNo) {
        // 演示：返回二维码连接
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        // 交易单号     金额      标题
        Map<Object, Object> build = MapUtil.builder()
                .put("out_trade_no", orderNo).put("total_amount", "100").put("subject", "iphone 15").build();
        request.setBizContent(JSONUtil.toJsonStr(build));
        AlipayClient client = client();
        try {
            AlipayTradePrecreateResponse response = client.execute(request);
            // 二维码链接
            String qrCode = response.getQrCode();
            return createQrCode(qrCode);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * 实现统一收单交易查询
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("query")
    public String query(String orderNo) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<Object, Object> build = MapUtil.builder().put("out_trade_no", orderNo).build();
        request.setBizContent(JSONUtil.toJsonStr(build));

        AlipayClient client = client();
        AlipayTradeQueryResponse response = client.execute(request);
        // 返回支付状态
        String tradeStatus = response.getTradeStatus();
        System.out.println(response);
        if (ObjectUtil.equals(tradeStatus, "TRADE_SUCCESS")) {
            return "交易成功";
        } else {
            return "交易失败";
        }

    }
    /**
     * 统一收单交易退款
     * @param orderNo   订单ID
     * @param refundNo  退款ID
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("refund")
    public String refund(String orderNo, String refundNo) throws AlipayApiException {
        // 返回退款请求 是否发起成功
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        // refundNo分多次退款需要提供退款单号，否则不提供也可以
        Map<Object, Object> build = MapUtil.builder()
                .put("out_trade_no", orderNo).put("refund_amount", "50.00").put("out_request_no", refundNo).build();
        request.setBizContent(JSONUtil.toJsonStr(build));
        AlipayClient client = client();
        AlipayTradeRefundResponse response = client.execute(request);
        String code = response.getCode();
        return code;
    }
    /**
     * 统一收单交易退款查询接口
     * @param orderNo
     * @param refundNo
     * @return
     */
    @RequestMapping("refundQuery")
    public String refundQuery(String orderNo, String refundNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        // 返回退款结果
        Map<Object, Object> build = MapUtil.builder().put("out_trade_no", orderNo).put("out_request_no", refundNo).build();
        request.setBizContent(JSONUtil.toJsonStr(build));
        AlipayClient client = client();
        AlipayTradeFastpayRefundQueryResponse response = client.execute(request);
        String refundStatus = response.getRefundStatus();
        if (refundStatus.equals("REFUND_SUCCESS")) {
            return "退款成功";
        } else {
            return "退款未到账";
        }
    }
    /**
     * 根据申请的沙箱环境对以下参数进行配置
     * @return
     */
    public AlipayClient client() {
        AlipayConfig alipayConfig = new AlipayConfig();
        // 设置网关地址
//        alipayConfig.setServerUrl("<-- 请填写您的支付宝网关地址 -->");
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        // 设置应用ID
//        alipayConfig.setAppId("<-- 请填写您的AppId -->");
        alipayConfig.setAppId("9021000138646212");
        // 设置应用私钥         签名 ==> 验签    明文 + 签名
//        alipayConfig.setPrivateKey("<-- 请填写您的应用私钥，例如：MIIEvQIBADANB ... ... -->");
        alipayConfig.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLktM48ZEguMxHYpxmWNBCI1jAS/GjwlssRoqzHuHvTsaW44iYKyXommP1EOMMOYProTXCGlRhp2LHXfFqgG7taR/9ABakJLmUPD/FEfLRJ4rAWRu7nXJRop8rBu/e9kqwCmx8fyihDcFS0rhCFM6U93vQAmtXzoQ05vpTyP7+5B2L3uic2jiLBBGMzi+CmcZUoR4D6SW23/JXBF7iKfSTsBkLsUQWco0mAMG9yd7yYGKYRRLpyQfgrxoNjhLKxFVL5WtI+PkSJ6aHbtSRZCKkwaUz7eT3jAWB8XVrsL7dJrytfuQnCv7FkBNNxJrPSBacNnMY7+PHpLJruA3hH6gZAgMBAAECggEAaH27dF4lcIHpLWg9/sFu6JTd1NkGRrsy1qrZG9B7BVXMhxpBIOrsrn8nZkkGMhi9EqLWRELB+Xn/EZLbMgiFA2g4coimaqyz4q5Yb4lIaB1aWzZa9NKiy0scLnI7EokA0Vp8bZglKq3Jld/74j0Bi7TcFJY7FrmdcJ2buy42+Uth/YTUwz4iF8s/6dNja+SupKr2F8+6zO+48ouC2qn9wsqLEg3L2UqfgH04XHOmlkxS+EljTV17Ry3Izm46ELQ4YNPNQu4qC86tttRqdS7Korg9YlX6H6xYycw9y5hsK596p9PZHKRRug3oudJII/NjgWkE4BBV+yBcOLCaIzZeIQKBgQDQ/8DNvnTQakUOG/QZ1jVr57b0xx+d3/WZg9yIyP/RZcFSWlfXgoQM0V1twS2dpSIHIkTs4y3+pF7VPQIkvF1m4Kgu4bF/AKqLSeKiLHbOc+OKLFb+U2DBC4UolPPpFImEaYwSQQuQN7q8fMiTR5hJvxHU5ynHuFymHpcRkvvvpwKBgQCq9jBK1EeTV1Dmsj5R86TfSAHFtiQN2MfWfxCu9FLKlEfyaezdnkqe08ylyoqf1fkktsKvvLggYLTdm5+hFzfsw8A6/afU7dZ3gH1fN1bEwnJUKQG8U+iFQUKPlYv/tnAF2G4z3Olb8iPMZzzio81Qcw32GJ5Ma38xHJmlK8iiPwKBgGgYmm1fiUstQwvN/HzJ5n3CQawVabf2jptRxAwVIOWo6usbuFjM1vyTYxeBejjkwNgQx4e9d3E3aUyIQIMPlu3gw6PmIcQCDu+lc04XG22k5xYxE3tFCBHerQoTdpEG+5+J+ow0/Y9tUoMDG9vPcxh4JGeIY5ToTK0o21GThgxNAoGBAIw2ucjFbaakOE/c5EvmXdRahEmZZtyMbOm48P/slnJbRt7EjWX9ywmUgAWBpkwM+/dmhSoIx6KWbgBLAJLoLg/bRC+dliaGoh0Ens6W3m0lZQstTqUc1Fll+qc0acvh3uKMbWXPyvyEDWQ5T7A508wlcM/jxQC8MWcgI8tZq4WVAoGAOiizlYcd0ZWFQ+UYudpshWfzguefZ1wBAcKH84eV4a3YEo6TbBva1hwoAhGBOw+2o4XTNTiEPBY3tiWs+dGtnBPJ3MAiwFloGaXY6A62IebzamkgwnyLZhX1FHOCc7mYkhqUthPQroXwe+VnKITj4dMzZ0FJl+qWCDiIlVeYZ9g=");
        // 设置请求格式，固定值json
        alipayConfig.setFormat("json");
        // 设置字符集
        alipayConfig.setCharset("gbk");
        // 设置签名类型
        alipayConfig.setSignType("RSA2");
        // 设置支付宝公钥
//        alipayConfig.setAlipayPublicKey("<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->");
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoHylNZk7ix5SYObqz6DlAyUZT8i+flM+6K/xxWBlRFqXmxcu1xTawAKE9aQVc8gcSILTd17ea/t0XMFyYrecE/8aKqg6pSq0C8AqM2+FQnNiWkl1VtD8L3d87d2JyxA1nzFfyBdz2HaZfHZK7nUPUIofIacfSB4H3l0V1In3JFVZzpp+sFGv3IPYEo5OF5J6pXLr+sIfELf0hBTuJcSG885sZMplt87+8o6XgeSyuODZuvF0M3RUfafHrFOHLcfcOpKFeRGXbeuaeS6sZvMFyNFl06D7ZDQc5dB6Gjo3Q3cDvIcmCEo+I+eRddxP3+kOa09Ir2pparlT9+KHc3VNaQIDAQAB");
        // 实例化客户端
        try {
            return new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.println("初始化支付宝配置错误");
            throw new RuntimeException("初始化支付宝配置错误");
        }
    }
    /**
     * 生成的Base64编码的二维码字符串
     * @param qrCode
     * @return
     */
    public String createQrCode(String qrCode) {
        return QrCodeUtil.generateAsBase64(qrCode, new QrConfig(500, 500), "jpg");
    }
}
