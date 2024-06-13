package org.example;

import org.example.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mmj
 * @Description
 * @create 2024-06-13 13:09
 */
@SpringBootTest
public class TestCommonConfig {
    @Autowired
    IdWorker idWorker;
    @Test
    public void testIdWorker(){
        System.out.println(idWorker.nextId());
    }
}
