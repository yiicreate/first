package com.test.first.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: lh
 * @version: 2020/12/22
 * @description:  rabbit 接收者
 */

@Component
@RabbitListener(queues = "hello")
public class HelloReceiver  {

    @RabbitHandler
    public void see(String str){
        System.out.println("接收者"+str);
    }
}
