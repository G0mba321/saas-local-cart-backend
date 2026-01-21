package com.local_cart.controller;

import com.local_cart.dto.request.BrandRequest;
import com.local_cart.dto.response.BrandResponse;
import com.local_cart.facade.BrandFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandFacade brandFacade;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid
            @RequestBody BrandRequest request) {

        BrandResponse response = brandFacade.createBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getOneBrand(
            @PathVariable Long id) {

        return ResponseEntity.ok(brandFacade.getOneBrand(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand
            (@PathVariable Long id, @RequestBody BrandRequest request) {

        BrandResponse response = brandFacade.updateBrand(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandFacade.getAllBrands();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandFacade.deleteBrand(id);
    }
}
