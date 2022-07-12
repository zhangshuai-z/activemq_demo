package com.zhangs.boot.activemq.topic.produce;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_Produce {
    //JMS模板
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    //主题目的地
    @Resource
    private Topic topic;

    //发送主题
    @Scheduled(fixedDelay = 3000)
    public void produceTopic() {
        jmsMessagingTemplate.convertAndSend(topic,"****主题消息：" + UUID.randomUUID().toString().substring(0,8));
    }
}
