package com.zhangs.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.TextMessage;

@Service
public class SpringMQ_Produce {

    @Resource
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        SpringMQ_Produce produce = (SpringMQ_Produce) applicationContext.getBean("springMQ_Produce");
        produce.jmsTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage("****spring和Active的整合case****");
            return textMessage;
        });
        System.out.println("*******send task over*******");
    }
}
