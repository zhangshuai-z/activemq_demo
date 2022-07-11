package com.zhangs.boot.activemq.prodece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_Produce {
    //JMS 模板
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    //队列目的地
    @Autowired
    private Queue queue;

    //发送消息
    public void produceMessage() {
        // 一参是目的地，二参是消息的内容
        jmsMessagingTemplate.convertAndSend(queue, "****" + UUID.randomUUID().toString().substring(1, 10));
    }
}
