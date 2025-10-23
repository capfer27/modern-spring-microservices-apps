package com.capfer.microservices.demo.twitter.kafka.service;

import com.capfer.microservices.demo.twitter.kafka.config.TwitterToKafkaConfig;
import com.capfer.microservices.demo.twitter.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import twitter4j.*;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.capfer.microservices.demo.twitter.kafka")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    private final TwitterToKafkaConfig twitterToKafkaConfig;

    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaConfig twitterToKafkaConfig, StreamRunner streamRunner) {
        this.twitterToKafkaConfig = twitterToKafkaConfig;
        this.streamRunner = streamRunner;
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
        streamRunner.start();
        //logTweets();
    }

    @Deprecated(forRemoval = true)
    public void logTweets() throws TwitterException {
        // getTweets example
        //--------------------------------------------------
        Twitter twitter = new TwitterFactory().getInstance();
        final TwitterV2 v2 = TwitterV2ExKt.getV2(twitter);
        final TweetsResponse tweets = v2.getTweets(
                new long[]{1519966129946791936L},
                V2DefaultFields.mediaFields, null, null, "attachments", null, "attachments.media_keys");
        LOGGER.info("tweets = {}", tweets);


        //--------------------------------------------------
        // getUsers example
        //--------------------------------------------------
        final long twitterDesignId = 87532773L;
        final UsersResponse users = v2.getUsers(new long[]{twitterDesignId}, null, null, "");
        LOGGER.info("users = {}", users);
    }
}
