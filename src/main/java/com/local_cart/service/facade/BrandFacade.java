package com.local_cart.service.facade;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.dto.response.BrandResponse;
import com.local_cart.entity.Brand;
import com.local_cart.entity.Country;
import com.local_cart.mapper.BrandMapper;
import com.local_cart.service.BrandService;
import com.local_cart.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BrandFacade {

    private final BrandService brandService;
    private final CountryService countryService;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandResponse createBrand(BrandRequest request) {
        Brand brandEntity = brandMapper.toEntity(request);

        Country country = countryService
                .findOneCountryById(Long.valueOf(request.getCountryId()));

        brandEntity.setCountryBrand(country);

        Brand savedBrand = brandService.saveBrand(brandEntity);

        return brandMapper.toResponse(savedBrand);
    }

    @Transactional(readOnly = true)
    public BrandResponse getOneBrand(Long id) {
        Brand brandToFind = brandService.findOneBrandById(id);

        return brandMapper.toResponse(brandToFind);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        return brandService.findAllBrands().stream()
                .map(brandMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Brand foundBrand = brandService.findOneBrandById(id);

        brandMapper.updateBrand(request, foundBrand);

        if (request.getCountryId() != null) {
            Country country = countryService
                    .findOneCountryById(Long.valueOf(request.getCountryId()));
            foundBrand.setCountryBrand(country);
        }

        Brand updateBrand = brandService.saveBrand(foundBrand);

        return brandMapper.toResponse(updateBrand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        brandService.deleteBrandById(id);
    }

}
