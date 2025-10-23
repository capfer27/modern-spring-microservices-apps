package com.capfer.microservices.demo.twitter.kafka.service;

public class TwitterToKafkaServiceException extends RuntimeException {

    public TwitterToKafkaServiceException() {
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }

    public TwitterToKafkaServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
