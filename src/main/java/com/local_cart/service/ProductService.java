package com.local_cart.service;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.entity.Brand;
import com.local_cart.entity.Category;
import com.local_cart.entity.Country;
import com.local_cart.entity.Product;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.mapper.ProductMapper;
import com.local_cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final BrandService brandService;
    private final CountryService countryService;
    private final CategoryService categoryService;

    @Transactional
    public Product createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        Brand brand = brandService.getOneBrand(request.getBrandId());
        Country country = countryService.getOneCountry(request.getCountryId());
        Category category = categoryService.getOneCategory(request.getCategoryId());

        product.setBrand(brand);
        product.setCountry(country);
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product getOneProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not found")
                );
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getOneProduct(id);

        productMapper.updateProduct(request, product);

        if (request.getBrandId() != null) {
            Brand brand = brandService.getOneBrand(request.getBrandId());
            product.setBrand(brand);
        }

        if (request.getCountryId() != null) {
            Country country = countryService.getOneCountry(request.getCountryId());
            product.setCountry(country);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryService.getOneCategory(request.getCategoryId());
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.delete(getOneProduct(id));
    }
}
