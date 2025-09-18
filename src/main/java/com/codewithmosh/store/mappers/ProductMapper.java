package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id",target = "categoryId")
    ProductDto toProductDto(Product product);

    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true) // Ignore the id field during update
    @Mapping(source = "categoryId",target = "category.id")
    void update(ProductDto productDto, @MappingTarget Product product);

}
