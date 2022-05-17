package com.xsn.springtestdemo.test.spring.boot;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class TestDemo {

    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(UserConfigurations.of(MainConfiguration.class));

    @Test
    public void shouldCustomizeInOrder() {
        this.contextRunner
                .withUserConfiguration(UserConfiguration.class)
                .run(context -> {
                    Customizer customizerOne = context.getBean("customizerOne", Customizer.class);
                    Customizer customizerTwo = context.getBean("customizerTwo", Customizer.class);
                    InOrder inOrder = Mockito.inOrder(customizerOne, customizerTwo);
                    then(customizerOne).should(inOrder).customize(any(TargetObject.class));
                    then(customizerTwo).should(inOrder).customize(any(TargetObject.class));
                    // then(customizerOne).should(inOrder).customize(any(TargetObject.class));
                });
    }

    static class MainConfiguration {

        @Bean
        public TargetObject targetObject(ObjectProvider<Customizer> customizers) {
            TargetObject targetObject = new TargetObject();
            customizers.orderedStream()
                    .forEach(customizer -> customizer.customize(targetObject));
            return targetObject;
        }
    }

    static class UserConfiguration {

        @Bean
        @Order(2)
        public Customizer customizerTwo() {
            return mock(Customizer.class);
        }

        @Bean
        @Order(1)
        public Customizer customizerOne() {
            return mock(Customizer.class);
        }

    }
}
