package com.example.serverless.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.serverless.dto.CreateProductDto;
import com.example.serverless.entity.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
	@Mapping(target = "id", ignore = true)
	Product toEntity(CreateProductDto dto);

	@Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(CreateProductDto dto, @MappingTarget Product entity);
}
