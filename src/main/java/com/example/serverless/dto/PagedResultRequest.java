package com.example.serverless.dto;

import java.util.Map;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class PagedResultRequest {
	private static final short DEFAULT_LIMIT = 10;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private int limit = DEFAULT_LIMIT;

	private Map<String, AttributeValue> lastKey;

	public static short getDefaultLimit() {
		return DEFAULT_LIMIT;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, AttributeValue> getLastKey() {
		return lastKey;
	}

	public void setLastKey(Map<String, AttributeValue> lastKey) {
		this.lastKey = lastKey;
	}
}
