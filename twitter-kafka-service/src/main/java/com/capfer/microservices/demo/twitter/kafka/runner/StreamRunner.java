package com.capfer.microservices.demo.twitter.kafka.runner;

import twitter4j.TwitterException;

//@FunctionalInterface
public interface StreamRunner {
    void start() throws TwitterException;
}
