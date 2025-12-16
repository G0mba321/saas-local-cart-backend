package com.local_cart.service;

import com.local_cart.entity.Brand;
import com.local_cart.exceptions.BrandNotFoundException;
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

    public Brand findOneBrandById(Long id) {
        return  getById(id);
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public void deleteBrandById(Long id) {
        brandRepository.delete(getById(id));
    }

    private Brand getById(Long id) {
        return  brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
    }
}
