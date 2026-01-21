package com.local_cart.service;

import com.local_cart.dto.request.CountryRequest;
import com.local_cart.entity.Country;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.mapper.CountryMapper;
import com.local_cart.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional
    public Country createCountry(CountryRequest request) {
        Country countryEntity = countryMapper.toEntity(request);

        Country saved = countryRepository.save(countryEntity);

        return saved;
    }

    public Country getOneCountry(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country is not found")
                );
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public Country updateCountry(Long id, CountryRequest request) {
        Country findCountry = getOneCountry(id);

        countryMapper.updateCountry(request, findCountry);

        return countryRepository.save(findCountry);
    }

    public void delete(Long id) {
        countryRepository.delete(getOneCountry(id));
    }
}
