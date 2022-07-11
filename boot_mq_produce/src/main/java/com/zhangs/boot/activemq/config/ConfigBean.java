package com.zhangs.boot.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {
    @Value("${myqueue}")
    private String myQueue;

    @Bean //类似 bean id = "" class = ""
    public ActiveMQQueue queue() {
        return new ActiveMQQueue(myQueue);
    }

}

