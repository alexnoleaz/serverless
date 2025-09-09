package com.example.serverless.dto;

import java.util.List;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public record PagedResult<T>(List<T> items, Map<String, AttributeValue> lastKey) {
}
