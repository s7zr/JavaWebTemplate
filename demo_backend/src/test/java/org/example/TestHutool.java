package org.example;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mmj
 * @Description
 * @create 2024-06-23 19:16
 */
@SpringBootTest
public class TestHutool {
    @Test
    public void TestCreateQrCode() {
        System.out.println(QrCodeUtil.generateAsBase64("https://www.baidu.com/", new QrConfig(500, 500), "jpg"));
    }
}
