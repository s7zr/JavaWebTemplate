package org.example;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author mmj
 * @Description
 * @create 2024-06-11 9:52
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan("org.example.mapper")//扫描mapper接口，生成代理对象，并被ioc容器管理
public class BackendApp {
    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class, args);
    }
}
