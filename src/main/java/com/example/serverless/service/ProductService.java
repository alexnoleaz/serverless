package com.example.serverless.service;

import org.springframework.stereotype.Service;

import com.example.serverless.dto.CreateProductDto;
import com.example.serverless.dto.PagedResult;
import com.example.serverless.dto.PagedResultRequest;
import com.example.serverless.entity.Product;
import com.example.serverless.exception.EntityNotFoundException;
import com.example.serverless.exception.ValueAlreadyUsedException;
import com.example.serverless.mapper.ProductMapper;
import com.example.serverless.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository repository;
	private final ProductMapper mapper;

	public ProductService(ProductRepository repository, ProductMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public Product create(CreateProductDto input) {
		checkNameIsUnique(input.name(), null);

		var product = mapper.toEntity(input);
		repository.insert(product);

		return product;
	}

	public Product find(String id) {
		return findByIdOrThrow(id);
	}

	public PagedResult<Product> findAll(PagedResultRequest request) {
		return repository.findAll(request);
	}

	public Product update(String id, CreateProductDto input) {
		checkNameIsUnique(input.name(), id);

		var dbProduct = findByIdOrThrow(id);
		mapper.updateFromDto(input, dbProduct);
		repository.update(dbProduct);

		return dbProduct;
	}

	public void delete(String id) {
		repository.delete(findByIdOrThrow(id));
	}

	private void checkNameIsUnique(String name, String excludeProductId) {
		var exists = excludeProductId == null
				? repository.existsByName(name)
				: repository.existsByNameAndIdNot(name, excludeProductId);

		if (exists)
			throw new ValueAlreadyUsedException("name", name);
	}

	private Product findByIdOrThrow(String id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id));
	}

}
