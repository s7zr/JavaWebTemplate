package org.example.controller.Pay;

/**
 * @author mmj
 * @Description
 * @create 2024-06-24 13:10
 */

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付宝 SDK 说明： https://opendocs.alipay.com/open/02np95
 */
@RestController
@RequestMapping("alipay_easy")
public class AlipayEasyController {
    @Autowired
    Config config;

    /**
     * 基于 Hutool 生成二维码
     * @param qrCode
     * @return
     */
    public String createQrCode(String qrCode) {
        return QrCodeUtil.generateAsBase64(qrCode, new QrConfig(500, 500), "jpg");
    }

    /**
     * 当面付 统一创建交易生成二维码接口
     * @param orderNo
     * @return
     */
    @RequestMapping("precreate")
    public String precreate(String orderNo) throws Exception {
        // 设置配置
        Factory.setOptions(config);
        String qrCode = Factory.Payment.FaceToFace()
                .asyncNotify("http://240a4030.r27.cpolar.top/alipay_easy/notify")
                .preCreate("神领物流运费", orderNo, "0.01").getQrCode();
        // 演示：返回二维码连接
        return createQrCode(qrCode);
    }

    /**
     * 实现统一收单交易查询
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("query")
    public String query(String orderNo) throws Exception {
        Factory.setOptions(config);
        AlipayTradeQueryResponse response = Factory.Payment.Common().query(orderNo);
        String tradeStatus = response.getTradeStatus();
        // 返回支付状态
        return tradeStatus;
    }

    /**
     * 统一收单交易退款
     * @param orderNo   订单ID
     * @param refundNo  退款ID
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("refund")
    public AlipayTradeRefundResponse refund(String orderNo, String refundNo) throws Exception {
        // 返回退款请求 是否发起成功
        Factory.setOptions(config);
        AlipayTradeRefundResponse response = Factory.Payment.Common().refund(orderNo, "0.01");
        return response;
    }

    /**
     * 统一收单交易退款查询接口
     * @param orderNo
     * @param refundNo
     * @return
     */
    @RequestMapping("refundQuery")
    public AlipayTradeFastpayRefundQueryResponse refundQuery(String orderNo, String refundNo) throws Exception {
        // 返回退款结果
        Factory.setOptions(config);
        // out_request_no  退款请求号。 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的商户订单号
        AlipayTradeFastpayRefundQueryResponse response = Factory.Payment.Common().queryRefund(orderNo, orderNo);
        return response;
    }
    /**
     * 支付成功异步通知接口
     * 文档： https://opendocs.alipay.com/open/194/103296?ref=api
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("notify")
    public String notify(HttpServletRequest request) throws Exception {
        // 1.接收参数，并将参数转为map
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<>();
        parameterMap.forEach((k, v) -> {
            paramMap.put(k, Arrays.stream(v).collect(Collectors.joining()));
        });

        // 2.验证签名
        Factory.setOptions(config);
        Boolean aBoolean = Factory.Payment.Common().verifyNotify(paramMap);
        if (!aBoolean) {
            throw new RuntimeException("验证签名失败");
        }

        System.out.println(paramMap);
        return "success";
    }
}
