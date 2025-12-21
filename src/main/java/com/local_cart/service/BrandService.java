package com.local_cart.service;

import com.local_cart.entity.Brand;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand findBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand is not found") {
                });
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public void deleteBrandById(Long id) {
        brandRepository.delete(findBrandById(id));
    }

}
