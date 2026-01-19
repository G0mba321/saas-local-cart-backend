package com.local_cart.service;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.entity.Brand;
import com.local_cart.entity.Country;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.mapper.BrandMapper;
import com.local_cart.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional
    public Brand createBrand(BrandRequest request, Country country) {
        Brand brandEntity = brandMapper.toEntity(request);

        brandEntity.setCountryBrand(country);

        return brandRepository.save(brandEntity);
    }

    public Brand getOneBrand(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand is not found")
                );
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional
    public Brand updateBrand(Long id, BrandRequest request, Country country) {
        Brand foundBrand = getOneBrand(id);

        brandMapper.updateBrand(request, foundBrand);

        if (request.getCountryId() != null) {
            foundBrand.setCountryBrand(country);
        }

        Brand updateBrand = brandRepository.save(foundBrand);

        return updateBrand;
    }

    public void deleteBrand(Long id) {
        brandRepository.delete(getOneBrand(id));
    }

}
