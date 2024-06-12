package org.example;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.mapper.SysUserMapper;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
    extends TestCase
{
    @Autowired
    UserService userService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Test
    public void user(){
        System.out.println(userService.getUserByUserName("admin"));
    }
    //测试mybatis的驼峰转换
    @Test
    public void testCamel(){
        System.out.println(sysUserMapper.selectByPrimaryKey1(1237361915165020161L));
    }
}
