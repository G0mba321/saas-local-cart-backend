package com.local_cart.service;

import com.local_cart.entity.Product;
import com.local_cart.exceptions.ProductNotFoundException;
import com.local_cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void delete(Long id) {
        productRepository.delete(findProduct(id));
    }
}
