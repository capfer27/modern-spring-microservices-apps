package com.capfer.microservices.demo.twitter.kafka.runner.impl;

import com.capfer.microservices.demo.twitter.kafka.config.TwitterToKafkaConfig;
import com.capfer.microservices.demo.twitter.kafka.listener.TwitterKafkaStatusListener;
import com.capfer.microservices.demo.twitter.kafka.runner.StreamRunner;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;

@Component
public class TwitterKafkaStreamRunnerImpl implements StreamRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStreamRunnerImpl.class);

    private final TwitterToKafkaConfig twitterToKafkaConfig;

    private final TwitterKafkaStatusListener twitterKafkaStatusListener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunnerImpl(TwitterToKafkaConfig twitterToKafkaConfig, TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.twitterToKafkaConfig = twitterToKafkaConfig;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    /**
     * Listen to Twitter messages continuously
     * @throws TwitterException
     */
    @Override
    public void start() throws TwitterException {
        this.twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        twitterStream.sample();
        addFilter();
    }

    // Spring will not call methods annotated with @PreDestroy
    // for beans with Prototype Scope.
    @PreDestroy
    public void shutdown() {
        if (twitterStream != null) {
            LOGGER.info("Closing twitter stream");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords = twitterToKafkaConfig.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOGGER.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
