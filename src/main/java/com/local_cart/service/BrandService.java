package com.local_cart.service;

import com.local_cart.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

}
