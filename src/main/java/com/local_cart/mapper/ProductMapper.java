package com.local_cart.mapper;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BrandMapper.class, CategoryMapper.class, CountryMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProduct(ProductRequest productRequest, @MappingTarget Product product);
}
