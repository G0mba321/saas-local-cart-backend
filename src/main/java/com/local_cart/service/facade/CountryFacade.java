package com.local_cart.service.facade;

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

    @Transactional
    public CountryResponse createCountry(CountryRequest request) {
        Country countryEntity = countryMapper.toEntity(request);

        Country saved = countryService.save(countryEntity);

        return countryMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CountryResponse getOneCountry(Long id) {
        Country findCountry = countryService.findOneCountryById(id);

        return countryMapper.toResponse(findCountry);
    }

    @Transactional(readOnly = true)
    public List<CountryResponse> getAllCountries() {
        return countryService.findAllCountries().stream()
                .map(countryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CountryResponse updateCountry(Long id, CountryRequest request) {
        Country findCountry = countryService.findOneCountryById(id);

        countryMapper.updateCountry(request, findCountry);

        Country updated = countryService.save(findCountry);

        return countryMapper.toResponse(updated);
    }

    @Transactional
    public void deleteCountry(Long id) {
        countryService.delete(id);
    }
}
