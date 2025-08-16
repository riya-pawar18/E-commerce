package com.ecommerce.store.mappers;
import com.ecommerce.store.dtos.ProductDto;
import com.ecommerce.store.entities.Product;
import com.ecommerce.store.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    @Mapping(target = "id",ignore = true)
    public void update(ProductDto productDto, @MappingTarget Product product);
}
