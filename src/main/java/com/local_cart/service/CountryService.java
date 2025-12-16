package com.local_cart.service;

import com.local_cart.entity.Country;
import com.local_cart.exceptions.CountryNotFoundException;
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

    public Country findOneCountryById(Long id) {
        return getById(id);
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public void delete(Long id) {
        countryRepository.delete(getById(id));
    }

    private Country getById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));
    }
}
