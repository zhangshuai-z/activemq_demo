package com.zhangs.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_TX {

    public static final String ACTIVEMQ_URL = "tcp://192.168.10.4:61616";
    public static final String QUEUE_NAME = "Queue-TX";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂,按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin","admin",ACTIVEMQ_URL);
        //2.通过连接工厂获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //第一个参数 事务； 第二个签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME); //类似 Collection collection = new ArrayList;

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //非持久性设置
//        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //持久性设置
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        try {
            //6.通过使用messageProducer生产3条消息发送到MQ的队列中
            for (int i = 1; i <= 3; i++) {
                //7.创建消息
                TextMessage textMessage = session.createTextMessage("TX msg---" + i);//理解为一个字符串
                //消息属性
//            textMessage.setStringProperty("c01","property01");
                //8.通过messageProducer发送给mq
                messageProducer.send(textMessage);


                //发送map类型消息
//              MapMessage mapMessage = session.createMapMessage();
//              mapMessage.setString("k1","v1");
//              messageProducer.send(mapMessage);

                //设置一个异常测试事务
//                if (i == 2) {
//                    throw new RuntimeException("测试事务异常");
//                }
            }

            //开启事务后，使用commit提交，这批消息才真正的被提交
            //事务提交
            session.commit();

        }catch (Exception e) {
            System.out.println("出现异常，消息回滚");
            //一般工作中，当代码出错时，我们在catch块中回滚，这样这批发送的消息就能回滚。
            session.rollback();
        } finally {
            //关闭资源
            messageProducer.close();
            session.close();
            connection.close();

            System.out.println("*******消息发送到mq成功******");
        }
    }
}
