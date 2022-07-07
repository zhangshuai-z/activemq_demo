package com.zhangs.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://192.168.10.4:61616";
    public static final String TOPIC_NAME = "ckelio-topic";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂按照给定的url采用默认的用户名
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.创建连接
        Connection connection = activeMQConnectionFactory.createConnection();

        //3.创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建destination
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        //设置持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //启动,先设置持久化后启动
        connection.start();
        //发送消息
        for (int i = 0; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("ckelio-topic主题消息--" + i);
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("*******ckelio-topic主题消息发送到mq成功******");

    }
}
