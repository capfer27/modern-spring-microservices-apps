package com.capfer.microservices.demo.twitter.kafka.runner.impl;

import com.capfer.microservices.demo.twitter.kafka.config.TwitterToKafkaConfig;
import com.capfer.microservices.demo.twitter.kafka.fixtures.FakerGenerator;
import com.capfer.microservices.demo.twitter.kafka.listener.TwitterKafkaStatusListener;
import com.capfer.microservices.demo.twitter.kafka.runner.StreamRunner;
import com.capfer.microservices.demo.twitter.kafka.service.TwitterToKafkaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "twitter-kafka-service.enable-mock-tweets", havingValue = "true")
public class MockKafkaStreamRunner implements StreamRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

    private final TwitterToKafkaConfig twitterToKafkaConfig;

    private final TwitterKafkaStatusListener twitterKafkaStatusListener;

    private static final Random RANDOM = new Random();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String[] WORDS = new String[]{
            "Lorem",
            "ipsum",
            "dolor",
            "sit",
            "amet",
            "consectetuer",
            "adipiscing",
            "elit",
            "Maecenas",
            "porttitor",
            "congue",
            "massa",
            "Fusce",
            "posuere",
            "magna",
            "sed",
            "pulvinar",
            "ultricies",
            "purus",
            "lectus",
            "malesuada",
            "libero"
    };

    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":{\"id\":\"{3}\"}" +
            "}";

    private static final String tweetAsRawJson2 = prepareJson("0", "1", "2", "3");

    private static String prepareJson(String createdAt, String id, String text, String userId) {
        return """
                {
                    "created_at": "{%s}",
                    "id": {%s},
                    "text": "{%s}",
                    "user": "{"id"}: "{%s}"
                }
                """.formatted(createdAt, id, text, userId);
    }

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public MockKafkaStreamRunner(TwitterToKafkaConfig twitterToKafkaConfig, TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.twitterToKafkaConfig = twitterToKafkaConfig;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        String[] keywords = twitterToKafkaConfig.getTwitterKeywords().toArray(String[]::new);
        int mockMinTweetLength = twitterToKafkaConfig.getMockMinTweetLength();
        int mockMaxTweetLength = twitterToKafkaConfig.getMockMaxTweetLength();
        long mockSleepsMs = twitterToKafkaConfig.getMockSleepMs();
        LOGGER.info("Starting mock filtering twitter streams for keywords {}", Arrays.toString(keywords));

        simulateTwitterStream(keywords, mockMinTweetLength, mockMaxTweetLength, mockSleepsMs);
    }

    private void simulateTwitterStream(String[] keywords, int mockMinTweetLength, int mockMaxTweetLength, long mockSleepsMs) {
        executorService.submit(() -> {
           try {
               while (true) {
                   String formattedTweet = FakerGenerator.generateFakeSentences(); // getFormattedTweet(keywords, mockMinTweetLength, mockMaxTweetLength);
                   Status status = TwitterObjectFactory.createStatus(formattedTweet);
                   twitterKafkaStatusListener.onStatus(status);
                   sleep(mockSleepsMs);
               }
           } catch (TwitterException e) {
               LOGGER.error("Error creating twitter status!", e);
           } finally {
               executorService.shutdownNow();
           }
        });
    }

    private void sleep(long mockSleepMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(mockSleepMs);
        } catch (InterruptedException e) {
            throw new TwitterToKafkaServiceException("Error while sleeping for waiting new status to create!!");
        }
    }

    private String getFormattedTweet(String[] keywords, int mockMinTweetLength, int mockMaxTweetLength) {
        String[] params = new String[] {
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomTweetContent(keywords, mockMinTweetLength, mockMaxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };

        return formatTweetAsJsonWithParams(params);
    }

    private String formatTweetAsJsonWithParams(String[] params) {
        String tweet = tweetAsRawJson;
        for (int i = 0; i < params.length; i++) {
            tweet = tweet.replace("{" + i + "}", params[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int mockMinTweetLength, int mockMaxTweetLength) {
        StringBuilder tweetBuilder = new StringBuilder();
        int tweetLength = RANDOM.nextInt(mockMaxTweetLength - mockMinTweetLength + 1) + mockMinTweetLength;

        return constructRandomTweet(keywords, tweetBuilder, tweetLength);
    }

    private String constructRandomTweet(String[] keywords, StringBuilder tweetBuilder, int tweetLength) {
        for (int i = 0; i < tweetLength; i++) {
            tweetBuilder.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweetBuilder.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }

        return tweetBuilder.toString().trim();
    }
}
