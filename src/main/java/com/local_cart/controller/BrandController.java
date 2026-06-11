package com.local_cart.controller;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.dto.response.BrandResponse;
import com.local_cart.facade.BrandFacade;
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
@RequestMapping("/api/brand")
@Tag(name = "BrandController",
        description = "Endpoints for crud operations on Brand service")
public class BrandController {

    private final BrandFacade brandFacade;

    @Operation(
            summary = "Create brand",
            description = "FIRSTLY MUST BE CREATED COUNTRY FOR 201 RESPONSE DUE TO countryBrand id")
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandFacade.createBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get one brand")
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getOneBrand(
            @PathVariable Long id) {

        return ResponseEntity.ok(brandFacade.getOneBrand(id));
    }

    @Operation(summary = "Update one brand",
            description = "IF USER WANTS TO UPDATE COUNTRY ON BRAND," +
                    "FIRSTLY CREATE COUNTRY FOR 200 RESPONSE DUE TO countryBrand id")
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id,
                                                     @RequestBody BrandRequest request) {

        BrandResponse response = brandFacade.updateBrand(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all brands",
            description = "Get a List of all brands"
    )
    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandFacade.getAllBrands();
    }

    @Operation(summary = "Delete one brand")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandFacade.deleteBrand(id);
    }
}
