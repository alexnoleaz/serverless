package com.example.serverless.dto;

import java.math.BigDecimal;

import com.example.serverless.entity.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductDto(
        @NotBlank @Size(max = Product.MAX_NAME_LENGTH) String name,
        @NotBlank @Size(max = Product.MAX_DESCRIPTION_LENGTH) String description,
        @Min(0) BigDecimal price) {
}
