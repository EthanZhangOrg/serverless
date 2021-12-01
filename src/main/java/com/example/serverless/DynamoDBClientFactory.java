package com.example.serverless;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBClientFactory {
    private final static DynamoDbClient client = DynamoDbClient.builder().region(Region.US_EAST_1).build();

    public static DynamoDbClient getClient() {
        return client;
    }
}
