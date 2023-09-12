package com.boilerplate.server.init;

import org.apache.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {

    /**
     * Dubbo消费者配置不主动监督注册中心服务
     *
     * @return
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        consumerConfig.setTimeout(40000);
        return consumerConfig;
    }
}


