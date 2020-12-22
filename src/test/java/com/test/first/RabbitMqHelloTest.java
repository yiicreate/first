package com.test.first;

import com.test.first.sender.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: lh
 * @version: 2020/12/22
 * @description:
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {
    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        for(int i = 0; i<10 ;i++){
            helloSender.send("车上"+i);
        }

    }

}
