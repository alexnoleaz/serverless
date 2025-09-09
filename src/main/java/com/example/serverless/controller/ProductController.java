package com.example.serverless.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.serverless.dto.CreateProductDto;
import com.example.serverless.dto.PagedResult;
import com.example.serverless.dto.PagedResultRequest;
import com.example.serverless.entity.Product;
import com.example.serverless.model.Response;
import com.example.serverless.service.ProductService;

import jakarta.validation.Valid;

@RequestMapping("/api/products")
@RestController
public class ProductController {
	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Response<Product>> create(@Valid @RequestBody CreateProductDto input) {
		return ResponseEntity.ok(Response.success(service.create(input)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Product>> find(@PathVariable("id") String id) {
		return ResponseEntity.ok(Response.success(service.find(id)));
	}

	@GetMapping
	public ResponseEntity<Response<PagedResult<Product>>> findAll(@Valid PagedResultRequest request) {
		return ResponseEntity.ok(Response.success(service.findAll(request)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<Product>> update(@PathVariable("id") String id,
			@Valid @RequestBody CreateProductDto input) {
		return ResponseEntity.ok(Response.success(service.update(id, input)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Void>> delete(@PathVariable("id") String id) {
		service.delete(id);
		return ResponseEntity.ok(Response.success());
	}
}
