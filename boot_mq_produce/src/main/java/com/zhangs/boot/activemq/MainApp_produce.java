package com.zhangs.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms //开启Jms适配
@EnableScheduling //是否开启定时任务调度功能
public class MainApp_produce {
    public static void main(String[] args) {
        SpringApplication.run(MainApp_produce.class,args);
    }
}
