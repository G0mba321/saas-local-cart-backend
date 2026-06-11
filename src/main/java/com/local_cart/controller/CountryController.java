package com.local_cart.controller;

import com.local_cart.dto.request.CountryRequest;
import com.local_cart.dto.response.CountryResponse;
import com.local_cart.facade.CountryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
@Tag(name = "CountryController",
        description = "Endpoints for crud operations on Country service")
public class CountryController {

    private final CountryFacade countryFacade;

    @Operation(summary = "Create country")
    @PostMapping
    public ResponseEntity<CountryResponse> createCountry(@Valid
                                                         @RequestBody CountryRequest request) {

        CountryResponse response = countryFacade.createCountry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get one country")
    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getOneCountry(@PathVariable Long id) {
        return ResponseEntity.ok(countryFacade.getOneCountry(id));
    }

    @Operation(
            summary = "Get all countries",
            description = "Get List of countries"
    )
    @GetMapping
    public List<CountryResponse> getAllCountries() {
        return countryFacade.getAllCountries();
    }

    @Operation(summary = "Update country")
    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable Long id, @Valid @RequestBody CountryRequest request) {

        CountryResponse response = countryFacade.updateCountry(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete one country")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryFacade.deleteCountry(id);
    }

}
