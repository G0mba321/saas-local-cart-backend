package com.local_cart.service;

import com.local_cart.entity.Category;
import com.local_cart.exceptions.CategoryNotFoundException;
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

    public Category findOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findRoot() {
        return categoryRepository.findByParentIsNull();
    }

    public void delete(Long id) {
        Category find = findOne(id);

        if (!find.getChildren().isEmpty()) {
            throw new CategoryNotFoundException(id);
        }

        categoryRepository.delete(find);
    }

}
