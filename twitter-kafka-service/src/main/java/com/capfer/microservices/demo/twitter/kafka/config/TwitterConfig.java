package com.capfer.microservices.demo.twitter.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

//@Data
//@Configuration
//@ConfigurationProperties(prefix = "twitter-config")
public class TwitterConfig {

//    @Value(value = "${twitter-config.debug}")
//    private boolean debug;
//
//    @Value(value = "${twitter-config.oauth-consumerKey}")
//    private String oauthConsumerKey;
//
//    @Value(value = "${twitter-config.oauth-consumerSecret}")
//    private String oauthConsumerSecret;
//
//    @Value(value = "${twitter-config.oauth-accessToken}")
//    private String oauthAccessToken;
//
//    @Value(value = "${twitter-config.oauth-accessTokenSecret}")
//    private String oauthAccessTokenSecret;

//    @Bean
//    public ConfigurationBuilder twitter4jConfigurationBuilder() {
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(debug)
//                .setOAuthConsumerKey(oauthConsumerKey)
//                .setOAuthConsumerSecret(oauthConsumerSecret)
//                .setOAuthAccessToken(oauthAccessToken)
//                .setOAuthAccessTokenSecret(oauthAccessTokenSecret)
//                .setHttpProxyHost("proxy")
//                .setHttpProxyPort(8080);
//        return cb;
//    }
//
//    @Bean
//    public TwitterStream twitterStream() {
//        return new TwitterStreamFactory(twitter4jConfigurationBuilder().build())
//                .getInstance();
//    }

//    @Bean
//    public Twitter twitter1() {
//        TwitterFactory tf = new TwitterFactory(twitter4jConfigurationBuilder().build());
//        return tf.getInstance();
//    }

//    @Bean
//    public Twitter twitter2(ConfigurationBuilder twitter4jConfigurationBuilder) {
//        TwitterFactory tf = new TwitterFactory(twitter4jConfigurationBuilder.build());
//        return tf.getInstance();
//    }
}
