package org.example;

import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mmj
 * @Description
 * @create 2024-06-13 15:18
 */
@SpringBootTest
public class TestUserService {
    @Autowired
    UserService userService;

    /**
     * 测试验证码接口
     */
    @Test
    public void testGetCaptchaCode(){
        System.out.println(userService.getCaptchaCode().getData());
    }
}
