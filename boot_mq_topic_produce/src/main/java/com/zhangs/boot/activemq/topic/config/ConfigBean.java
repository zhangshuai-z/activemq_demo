package com.zhangs.boot.activemq.topic.config;

import com.zhangs.boot.activemq.topic.produce.Topic_Produce;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ConfigBean {

    @Value("${myTopic}")
    private String myTopic;

    @Bean
    public ActiveMQTopic topic() {
        return new ActiveMQTopic(myTopic);
    }

}
