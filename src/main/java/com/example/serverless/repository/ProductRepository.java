package com.example.serverless.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.serverless.dto.PagedResult;
import com.example.serverless.dto.PagedResultRequest;
import com.example.serverless.entity.Product;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
public class ProductRepository {
	private final DynamoDbTable<Product> table;

	public ProductRepository(DynamoDbEnhancedClient enhancedClient) {
		table = enhancedClient.table("products", TableSchema.fromBean(Product.class));
	}

	public Product insert(Product product) {
		product.setId(UUID.randomUUID().toString());
		table.putItem(product);
		return product;
	}

	public Optional<Product> findById(String id) {
		return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
	}

	public PagedResult<Product> findAll(PagedResultRequest request) {
		var builder = ScanEnhancedRequest.builder().limit(request.getLimit());
		var lastKey = request.getLastKey();

		if (lastKey != null && !lastKey.isEmpty())
			builder.exclusiveStartKey(lastKey);

		var result = table.scan(builder.build()).iterator().next();
		return new PagedResult<>(result.items(), result.lastEvaluatedKey());
	}

	public Product update(Product product) {
		table.updateItem(product);
		return product;
	}

	public void delete(Product product) {
		table.deleteItem(product);
	}

	public boolean existsByName(String name) {
		var attrNames = Map.of("#n", "name");
		var attrValues = Map.of(":name", AttributeValue.builder().s(name).build());

		var expression = Expression.builder()
				.expression("#n = :name")
				.expressionNames(attrNames)
				.expressionValues(attrValues)
				.build();

		var scan = table.scan(
				r -> r.filterExpression(expression)
						.limit(1));

		return scan.items().iterator().hasNext();
	}

	public boolean existsByNameAndIdNot(String name, String excludeId) {
		var attrNames = Map.of(
				"#n", "name",
				"#i", "id");

		var attrValues = Map.of(
				":name", AttributeValue.builder().s(name).build(),
				":id", AttributeValue.builder().s(excludeId).build());

		var expression = Expression.builder()
				.expression("#n = :name AND #i <> :id")
				.expressionNames(attrNames)
				.expressionValues(attrValues)
				.build();

		var scan = table.scan(
				r -> r.filterExpression(expression)
						.limit(1));

		return scan.items().iterator().hasNext();
	}
}
