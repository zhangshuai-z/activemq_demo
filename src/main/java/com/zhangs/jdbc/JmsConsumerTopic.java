package com.zhangs.jdbc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumerTopic {

    public static final String ACTIVEMQ_URL = "nio://192.168.10.4:61608";
    public static final String TOPIC_NAME = "JDBC-topic";

    public static void main(String[] args) throws JMSException, IOException {

        //创建connection factory连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();

        connection.setClientID("zhangs01");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);
        session.createDurableSubscriber(topic,"mq-jdbc");
        connection.start();

        messageConsumer.setMessageListener((Message message) -> {
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者接收到Topic消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
