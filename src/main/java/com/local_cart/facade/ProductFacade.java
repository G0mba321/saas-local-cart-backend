package com.local_cart.facade;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.entity.Product;
import com.local_cart.mapper.ProductMapper;
import com.local_cart.service.BrandService;
import com.local_cart.service.CategoryService;
import com.local_cart.service.CountryService;
import com.local_cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductFacade {

    private final ProductService productService;
    private final ProductMapper productMapper;

    private final BrandService brandService;
    private final CountryService countryService;
    private final CategoryService categoryService;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productService.createProduct(request);

        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getOneProduct(Long id) {
        Product find = productService.getOneProduct(id);

        return productMapper.toResponse(find);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productService.updateProduct(id, request);

        return productMapper.toResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productService.delete(id);
    }
}
