package com.capfer.microservices.demo.twitter.kafka.service;

import com.capfer.microservices.demo.twitter.kafka.config.TwitterToKafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.capfer.microservices.demo.twitter.kafka")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final TwitterToKafkaConfig twitterToKafkaConfig;

    public TwitterToKafkaServiceApplication(TwitterToKafkaConfig twitterToKafkaConfig) {
        this.twitterToKafkaConfig = twitterToKafkaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Application starts...");
        List<String> twitterKeywords = twitterToKafkaConfig.getTwitterKeywords();
        LOGGER.info(Arrays.toString(twitterKeywords.toArray(new String[]{})));
        LOGGER.info(twitterToKafkaConfig.getWelcomeMessage());
        LOGGER.info("Application terminates...");
    }
}
