package org.example.controller.Pay;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.file.IOUtils;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.example.config.WXPayConfigCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wxpay")
public class WxPayController {
    @Autowired
    Config config;
    /**
     * 当面付 统一创建交易生成二维码接口
     * @param orderNo
     * @return
     */
    @RequestMapping("precreate")
    public String precreate(String orderNo) throws Exception {
        WXPay wxPay = new WXPay(new WXPayConfigCustom());

        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("body", "神领物流运费");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("total_fee", "1");
        paramMap.put("spbill_create_ip", "123.12.12.123");
        paramMap.put("notify_url", "http://240a4030.r27.cpolar.top/wxpay/notify");
        paramMap.put("trade_type", "NATIVE");

        Map<String, String> result = wxPay.unifiedOrder(paramMap);
        String code_url = result.get("code_url");

        // 演示：返回二维码连接
        return createQrCode(code_url);
    }

    /**
     * 实现统一收单交易查询
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("query")
    public String query(String orderNo) throws Exception {
        // 返回支付状态
        WXPay wxPay = new WXPay(new WXPayConfigCustom());

        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("out_trade_no", orderNo);
        Map<String, String> result = wxPay.orderQuery(paramMap);
        return result.get("trade_state");
    }

    /**
     * 统一收单交易退款
     * @param orderNo   订单ID
     * @param refundNo  退款ID
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("refund")
    public Map<String, String> refund(String orderNo, String refundNo) throws Exception {
        // 返回退款请求 是否发起成功
        WXPay wxPay = new WXPay(new WXPayConfigCustom());

        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("out_refund_no", refundNo);
        paramMap.put("total_fee", "1");
        paramMap.put("refund_fee", "1");
        Map<String, String> result = wxPay.refund(paramMap);
        return result;
    }

    /**
     * 统一收单交易退款查询接口
     * @param orderNo
     * @param refundNo
     * @return
     */
    @RequestMapping("refundQuery")
    public Map<String, String> refundQuery(String orderNo, String refundNo) throws Exception {
        // 返回退款结果
        WXPay wxPay = new WXPay(new WXPayConfigCustom());

        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("out_refund_no", refundNo);
        Map<String, String> result = wxPay.refundQuery(paramMap);
        return result;
    }

    /**
     * 支付成功后通知接口
     * 文档： https://opendocs.alipay.com/open/194/103296?ref=api
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("notify")
    public String notify(HttpServletRequest request) throws Exception {
        //读取request请求体中的xmL参数并转为map
        try {
            Map<String,String> resultMap = new HashMap();
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            //加入自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            WXPay wXPay = new WXPay(new WXPayConfigCustom());
            // 1．验证签名
            boolean signatureValid  = wXPay.isResponseSignatureValid(map);
            if (!signatureValid) {
                System.out.println("验证签名失败");
                resultMap.put( "return_code" , "FAIL");
                resultMap.put( "return_msg","验证签名失败");
                return WXPayUtil.mapToXml(resultMap);
            }
            // 2．根据交易单Id查询数据库中的交易单
            System.out.println("根据交易单Id 查询数据库中的交易单:" + map.get("out_trade_no"));
            // 3．对比金额是否一致
            // 加分布式锁
            //4．判断交易单的状态
            // 5．根据支付的结果通知修改交易单状态
            //解锁
            // 6．返回微信支付结果
            resultMap.put( "return_code" , "SUCCESS");
            resultMap.put( "return_msg","处理成功");
            return WXPayUtil.mapToXml(resultMap);
        } catch (Exception e){
            throw new RuntimeException();
        }


    }
    /**
     * 基于 Hutool 生成二维码
     * @param qrCode
     * @return
     */
    public String createQrCode(String qrCode) {
        return QrCodeUtil.generateAsBase64(qrCode, new QrConfig(500, 500), "jpg");
    }
}