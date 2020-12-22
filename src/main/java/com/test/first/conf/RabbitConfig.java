package com.test.first.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lh
 * @version: 2020/12/22
 * @description:  rabbit 配置类
 */

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return  new Queue("hello");
    }


}
