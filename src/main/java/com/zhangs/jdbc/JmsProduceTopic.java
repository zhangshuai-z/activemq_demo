package com.zhangs.jdbc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceTopic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.10.4:61608";
    public static final String TOPIC_NAME = "JDBC-topic";

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
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        //发送消息
        for (int i = 0; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("JDBC-topic主题消息--" + i);
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("*******JDBC-topic主题消息发送到mq成功******");

    }
}
