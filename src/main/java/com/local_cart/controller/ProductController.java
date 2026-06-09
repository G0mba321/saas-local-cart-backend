package com.local_cart.controller;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.facade.ProductFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductFacade productFacade;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid
                                                         @RequestBody ProductRequest request) {

        ProductResponse response = productFacade.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOneProduct(
            @PathVariable Long id) {
        return ResponseEntity.ok(productFacade.getOneProduct(id));
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productFacade.getAllProducts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid
                                                         @PathVariable Long id, @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productFacade.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productFacade.deleteProduct(id);
    }
}
