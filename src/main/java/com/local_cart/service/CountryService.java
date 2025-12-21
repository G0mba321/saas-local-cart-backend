package com.local_cart.service;

import com.local_cart.entity.Country;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public Country findCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country is not found") {
                });
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public void delete(Long id) {
        countryRepository.delete(findCountryById(id));
    }
}
