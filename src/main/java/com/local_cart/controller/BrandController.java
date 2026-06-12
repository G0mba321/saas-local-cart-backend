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
@Tag(name = "brand-controller",
        description = "endpoints for crud operations on brand service")
public class BrandController {

    private final BrandFacade brandFacade;

    @Operation(
            summary = "create brand",
            description = "firstly must be created country for 201 response due to country_brand_id")
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandFacade.createBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "get one brand")
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getOneBrand(
            @PathVariable Long id) {

        return ResponseEntity.ok(brandFacade.getOneBrand(id));
    }

    @Operation(summary = "update one brand",
            description = "if user wants to update country on brand," +
                    "firstly create country for 200 response due to country_brand_id")
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id,
                                                     @RequestBody BrandRequest request) {

        BrandResponse response = brandFacade.updateBrand(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "get all brands",
            description = "get a list of all brands"
    )
    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandFacade.getAllBrands();
    }

    @Operation(summary = "delete one brand")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandFacade.deleteBrand(id);
    }
}
