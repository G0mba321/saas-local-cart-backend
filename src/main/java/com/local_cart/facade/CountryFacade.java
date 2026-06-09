package com.local_cart.facade;

import com.local_cart.dto.request.CountryRequest;
import com.local_cart.dto.response.CountryResponse;
import com.local_cart.entity.Country;
import com.local_cart.mapper.CountryMapper;
import com.local_cart.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CountryFacade {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    public CountryResponse createCountry(CountryRequest request) {
        Country country = countryService.createCountry(request);

        return countryMapper.toResponse(country);
    }

    @Transactional(readOnly = true)
    public CountryResponse getOneCountry(Long id) {
        Country findCountry = countryService.getOneCountry(id);

        return countryMapper.toResponse(findCountry);
    }

    @Transactional(readOnly = true)
    public List<CountryResponse> getAllCountries() {
        return countryService.findAllCountries().stream()
                .map(countryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CountryResponse updateCountry(Long id, CountryRequest request) {
        Country country = countryService.updateCountry(id, request);

        return countryMapper.toResponse(country);
    }

    @Transactional
    public void deleteCountry(Long id) {
        countryService.delete(id);
    }
}
