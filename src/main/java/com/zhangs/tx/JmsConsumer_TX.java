package com.zhangs.tx;

import com.zhangs.common.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_TX {

    public static final String QUEUE_NAME = "Queue-TX";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnection activeMQConnection = new ActiveMQConnection();
        Connection connection = activeMQConnection.ActiveMQConnection();
        connection.start();

        //3.创建会话session，两个参数transacted=事务，acknowledgeMode=确认模式（签收）
        //消费者开启了事务就必须手动提交，不然会重复消费消息
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //方法一
        /*
        同步阻塞方法（receive()）
        订阅者或接收者调用MessageConsumer的receive()方法来接收消息，receive方法在能够接收到消息之前（或超时之前）将一直阻塞
        while (true) {
            TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);
            if (null != textMessage) {
                System.out.println("*****消费者接收到消息*****" + textMessage.getText());
            } else {
                break;
            }
        }

        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();*/

        //方法二
        //通过监听的方式来监听消息  MessageConsumer messageConsumer = session.createConsumer(queue);
        //异步非阻塞方法（监听器onMessage()）
        //订阅者或接收者通过messageConsumer的setMessageListener(new MessageListener() 注册一个消息监听器
        //当消息到达后，系统会自动调用监听器 onMessage(Message message) 方法
        messageConsumer.setMessageListener(new MessageListener() {
            int a = 0;
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("****消费者接收到消息****" + textMessage.getText());
//                        if (a == 0) {
//                            System.out.println("commit");
//                            session.commit();
//                        }
//                        if (a == 2) {
//                            System.out.println("rollback");
//                            session.rollback();
//                        }
                        a++;

                        //消息签收
                        textMessage.acknowledge();
                        //接受消息属性
//                        System.out.println("****消费者接收到消息属性***" + textMessage.getStringProperty("c01"));


                    } catch (Exception e) {
                        System.out.println("出现异常，消费失败，放弃消费");
                        try {
                            session.rollback();
                        } catch (JMSException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                //接收map类型的消息
//                if (null != message && message instanceof MapMessage) {
//                    MapMessage mapMessage = (MapMessage) message;
//                    try {
//                        System.out.println("****消费者接收到消息****" + mapMessage.getString("k1"));
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        });

        System.in.read();//保证控制台不灭
        messageConsumer.close();
        session.close();
        connection.close();

        /**
         * 1. 先生产     只启动一号消费者。 问题：1号消费者能消费消息吗？
         *  可以消费
         * 2. 先生产     先启动一号消费者再启动二号消费者。 问题：2号消费者还能消费消息吗？
         *  2.1 1号可以消费
         *  2.2 2号不可以消费
         * 3. 先启动两个消费者，再生产六条消息，消息情况如何？
         *  3.1 两个消费者都有六条  N
         *  3.2 先到先得 六条全给一个 N
         *  3.3 一人一半 Y
         */
    }
}
