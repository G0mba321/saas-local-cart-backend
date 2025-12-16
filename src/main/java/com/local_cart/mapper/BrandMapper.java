package com.local_cart.mapper;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.dto.response.BrandResponse;
import com.local_cart.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CountryMapper.class)
public interface BrandMapper {

    Brand toEntity(BrandRequest brandRequest);

    BrandResponse toResponse(Brand brand);

    void updateBrand(BrandRequest brandRequest, @MappingTarget Brand brand);
}
