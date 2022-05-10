package com.xsn.springtestdemo.test.spring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
public class WebConfig {

    @GetMapping("/test")
    public String test(@RequestParam String name) {

        return "hello " + name;
    }
}
