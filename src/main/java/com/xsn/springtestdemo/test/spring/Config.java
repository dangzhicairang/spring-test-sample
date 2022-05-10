package com.xsn.springtestdemo.test.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public HelloService helloService() {

        return new HelloService() {
            @Override
            public String hello() {
                return "hello world";
            }
        };
    }
}
