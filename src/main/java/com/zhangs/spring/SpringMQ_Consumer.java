package com.zhangs.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class SpringMQ_Consumer {

    @Resource
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        SpringMQ_Consumer springMQConsumer = (SpringMQ_Consumer) applicationContext.getBean("springMQ_Consumer");
        String retValue = (String) springMQConsumer.jmsTemplate.receiveAndConvert();
        System.out.println("***消费者接收到消息***" + retValue);
//        System.in.read();
    }
}
