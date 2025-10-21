package com.capfer.microservices.demo.twitter.kafka.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-kafka-service")
public class TwitterToKafkaConfig
{
    private List<String> twitterKeywords;
    private String welcomeMessage;
}
