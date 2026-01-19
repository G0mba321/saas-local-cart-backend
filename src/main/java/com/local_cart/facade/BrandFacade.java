package com.local_cart.facade;

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


    public BrandResponse createBrand(BrandRequest request) {
        Country country = countryService.getOneCountry(request.getCountryId());

        Brand brand = brandService.createBrand(request, country);

        return brandMapper.toResponse(brand);
    }

    @Transactional(readOnly = true)
    public BrandResponse getOneBrand(Long id) {
        Brand brandToFind = brandService.getOneBrand(id);

        return brandMapper.toResponse(brandToFind);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands().stream()
                .map(brandMapper::toResponse)
                .collect(Collectors.toList());
    }


    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Country country = countryService.getOneCountry(request.getCountryId());

        Brand brand = brandService.updateBrand(id, request, country);

        return brandMapper.toResponse(brand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        brandService.deleteBrand(id);
    }

}
