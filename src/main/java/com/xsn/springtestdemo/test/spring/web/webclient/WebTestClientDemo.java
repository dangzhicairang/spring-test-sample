package com.xsn.springtestdemo.test.spring.web.webclient;

import com.xsn.springtestdemo.test.spring.web.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfig.class)
public class WebTestClientDemo {

    @Test
    public void test1() {
        WebTestClient webTestClient = MockMvcWebTestClient.bindToController(WebConfig.class)
                .build();
        webTestClient
                .get()
                .uri("/test?name=dd")
                .exchange()
                .expectBody(String.class).isEqualTo("hello dd");
    }

}
