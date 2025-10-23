package com.capfer.microservices.demo.twitter.kafka.fixtures;

import com.github.javafaker.Faker;

public class FakerGenerator {

    public static String generateFakeSentences() {
        StringBuilder builder = new StringBuilder();
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String artistName = faker.artist().name();
        String material = faker.commerce().material();

        return builder.append(name)
                .append(" ")
                .append(artistName)
                .append(" ")
                .append(material)
                .toString();
    }
}
