package com.local_cart.controller;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.facade.ProductFacade;
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
@RequestMapping("/api/product")
@Tag(name = "ProductController",
        description = "Endpoints for crud operations on Product service" +
                " BEFORE CREATING PRODUCT MUST BE CREATED IN ORDER 1.COUNTRY, 2.BRAND, 3.CATEGORY")
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "Create product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid
                                                         @RequestBody ProductRequest request) {

        ProductResponse response = productFacade.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get one product")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOneProduct(
            @PathVariable Long id) {
        return ResponseEntity.ok(productFacade.getOneProduct(id));
    }

    @Operation(
            summary = "Get all products",
            description = "Get a List of all products"
    )
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productFacade.getAllProducts();
    }

    @Operation(summary = "Update one product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid
                                                         @PathVariable Long id, @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productFacade.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete one product")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productFacade.deleteProduct(id);
    }
}
