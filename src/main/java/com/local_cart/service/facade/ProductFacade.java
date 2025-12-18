package com.local_cart.service.facade;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.entity.Brand;
import com.local_cart.entity.Category;
import com.local_cart.entity.Country;
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
        Product productEntity = productMapper.toEntity(request);

        Brand brand = brandService.findOneBrandById(request.getBrandId());
        Country country = countryService.findOneCountryById(request.getCountryId());
        Category category = categoryService.findOneCategoryById(request.getCategoryId());

        productEntity.setBrand(brand);
        productEntity.setCountry(country);
        productEntity.setCategory(category);

        Product saveProduct = productService.save(productEntity);

        return productMapper.toResponse(saveProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getOneProduct(Long id) {
        Product find = productService.findProduct(id);

        return productMapper.toResponse(find);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productService.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product findProduct = productService.findProduct(id);

        productMapper.updateProduct(request, findProduct);

        if (request.getBrandId() != null) {
            Brand brand = brandService.findOneBrandById(request.getBrandId());
            findProduct.setBrand(brand);
        }

        if (request.getCountryId() != null) {
            Country country = countryService.findOneCountryById(request.getCountryId());
            findProduct.setCountry(country);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryService.findOneCategoryById(request.getCategoryId());
            findProduct.setCategory(category);
        }

        Product updated = productService.save(findProduct);

        return productMapper.toResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productService.delete(id);
    }
}
