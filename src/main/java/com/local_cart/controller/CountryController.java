package com.local_cart.controller;

import com.local_cart.dto.request.CountryRequest;
import com.local_cart.dto.response.CountryResponse;
import com.local_cart.facade.CountryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryFacade countryFacade;

    @PostMapping
    public ResponseEntity<CountryResponse> createCountry(
            @RequestBody CountryRequest request) {

        CountryResponse response = countryFacade.createCountry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getOneCountry(@PathVariable Long id) {
        return ResponseEntity.ok(countryFacade.getOneCountry(id));
    }

    @GetMapping
    public List<CountryResponse> getAllCountries() {
        return countryFacade.getAllCountries();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable Long id, @RequestBody CountryRequest request) {

        CountryResponse response = countryFacade.updateCountry(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryFacade.deleteCountry(id);
    }

}
