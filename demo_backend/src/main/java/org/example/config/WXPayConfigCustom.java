package org.example.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class WXPayConfigCustom extends WXPayConfig {

    /**
     * 开发者(AppID)
     * @return
     */
    @Override
    public String getAppID() {

        return "wx74862e0dfcf69954";
    }

    /**
     * 商户号
     * @return
     */
    @Override
    public String getMchID() {

        return "1558950191";
    }

    /**
     * API密钥
     * @return
     */
    @Override
    public String getKey() {

        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }

    /**
     * 退款：必须强制使用API证书
     * @return
     */
    @Override
    public InputStream getCertStream() {
        try {
            String path = ClassLoader.getSystemResource("").getPath();
            return new FileInputStream(new File(path + "apiclient_cert.p12"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String s, long l, Exception e) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new IWXPayDomain.DomainInfo("api.mch.weixin.qq.com", true);
            }
        };
    }


}