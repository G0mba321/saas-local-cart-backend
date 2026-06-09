package com.local_cart.mapper;

import com.local_cart.dto.request.CountryRequest;
import com.local_cart.dto.response.CountryResponse;
import com.local_cart.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {

    Country toEntity(CountryRequest countryRequest);

    CountryResponse toResponse(Country country);

    void updateCountry(CountryRequest countryRequest, @MappingTarget Country country);
}
