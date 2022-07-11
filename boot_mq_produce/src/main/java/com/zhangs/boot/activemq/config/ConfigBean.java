package com.zhangs.boot.activemq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {
    @Value("${myqueue}")
    private String myQueue;
}

