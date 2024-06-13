package org.example.config;

import org.example.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mmj
 * @Description
 * @create 2024-06-13 13:08
 */
@Configuration
public class CommonConfig {
    /**
     * 配置id生成器bean
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        //基于运维人员对机房和机器的编号规划自行约定
        /**
         * 参数1:机器ID参数2:机房ID
         * 机房和机器编号一般由运维人员进行唯一性规划
         */
        return new IdWorker(1l,2l);
    }

}
