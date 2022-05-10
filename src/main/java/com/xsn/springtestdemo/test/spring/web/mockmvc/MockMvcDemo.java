package com.xsn.springtestdemo.test.spring.web.mockmvc;

import com.xsn.springtestdemo.test.spring.web.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = WebConfig.class)
public class MockMvcDemo {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void test1() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        /*mockMvc.perform(
                get("/test").param("name", "dd")
        );*/
    }
}
