package com.xsn.springtestdemo.test.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = Config.class)
// @SpringJUnitConfig
@ExtendWith(SpringExtension.class)
public class ContextConfigurationAnnoDemo {

    @Autowired
    HelloService helloService;

    @Test
    public void test1() {
        System.out.println(helloService.hello());
    }
}
