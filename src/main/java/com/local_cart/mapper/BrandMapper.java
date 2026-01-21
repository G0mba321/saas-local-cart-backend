package com.local_cart.mapper;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.dto.response.BrandResponse;
import com.local_cart.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CountryMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    @Mapping(target = "countryBrand", ignore = true)
    Brand toEntity(BrandRequest brandRequest);

    @Mapping(target = "countryBrand")
    BrandResponse toResponse(Brand brand);

    @Mapping(target = "countryBrand", ignore = true)
    void updateBrand(BrandRequest brandRequest, @MappingTarget Brand brand);
}
