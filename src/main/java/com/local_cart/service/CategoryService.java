package com.local_cart.service;

import com.local_cart.entity.Category;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category is not found") {
                });
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findRoots() {
        return categoryRepository.findByParentIsNull();
    }

    public void delete(Long id) {
        Category find = findCategoryById(id);

        if (!find.getChildren().isEmpty()) {
            throw new ResourceNotFoundException("Category is not found") {
            };
        }

        categoryRepository.delete(find);
    }

}
