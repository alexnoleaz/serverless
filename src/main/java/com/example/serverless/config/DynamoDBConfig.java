package com.example.serverless.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;

@Configuration
public class DynamoDBConfig {
	@Value("${amazon.dynamodb.local:false}")
	private boolean useLocalDynamo;

	@Bean
	DynamoDbClient dynamoDbClient() {
		if (useLocalDynamo)
			return DynamoDbClient.builder()
					.endpointOverride(URI.create("http://localhost:8000"))
					.region(Region.SA_EAST_1)
					.build();

		return DynamoDbClient.builder()
				.httpClient(ApacheHttpClient.builder().build())
				.region(Region.SA_EAST_1)
				.build();
	}

	@Bean
	DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
		return DynamoDbEnhancedClient.builder()
				.dynamoDbClient(dynamoDbClient)
				.build();
	}
}
