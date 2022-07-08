package com.zhangs.common;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;


public class ActiveMQConnection {
    public static final String ACTIVEMQ_URL = "tcp://192.168.10.4:61616";
    public Connection ActiveMQConnection() throws JMSException {

        //1.创建连接工厂,按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "admin", ACTIVEMQ_URL);
        //2.通过连接工厂获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        return connection;
    }
}