package com.zhangs.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.10.4:61616";
    public static final String TOPIC_NAME = "ckelio-topic";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是2号订阅者");
        //创建connection factory连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("2号订阅者");

        // 3 创建回话  session
        // 两个参数，第一个事务， 第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地 （两种 ： 队列/主题   这里用主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark...");

        // 5 发布订阅
        connection.start();

        Message message = topicSubscriber.receive();// 一直等
        while (null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println(" 收到的持久化 topic ："+textMessage.getText());
            message = topicSubscriber.receive();
        }

        session.close();
        connection.close();

        /**
         * 1. 一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题。
         * 2. 然后再运行生产者发送消息。
         * 3. 此时，无论消费者是否在线，都会收到。不在线的话，下次再连接的时候，会把没有收过的消息都接收下来。
         */
    }
}
