package com.local_cart.service.facade;

import com.local_cart.service.BrandService;
import com.local_cart.service.CountryService;
import lombok.RequiredArgsConstructor;
import mapper.BrandMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BrandFacade {

    private final BrandService brandService;
    private final CountryService countryService;
    private final BrandMapper brandMapper;
}
