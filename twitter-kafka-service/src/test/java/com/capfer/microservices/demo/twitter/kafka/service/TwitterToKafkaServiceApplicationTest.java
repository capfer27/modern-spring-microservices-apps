package com.capfer.microservices.demo.twitter.kafka.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TwitterToKafkaServiceApplicationTest {

    @Test
    public void contextLoad() {
        Assertions.assertEquals("test", "test");
    }
}