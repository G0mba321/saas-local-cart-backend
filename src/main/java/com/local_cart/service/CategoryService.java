package com.local_cart.service;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.entity.Category;
import com.local_cart.exceptions.ConflictException;
import com.local_cart.exceptions.ResourceNotFoundException;
import com.local_cart.mapper.CategoryMapper;
import com.local_cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public Category createCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);

        if (request.getParentId() != null) {
            Category parent = getOneCategory(request.getParentId());
            category.setParent(parent);
            category.setRoot(false);
        } else {
            category.setRoot(true);
        }

        return categoryRepository.save(category);
    }

    public Category getOneCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category is not found " + id)
                );
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findRoots() {
        return categoryRepository.findByParentIsNull();
    }

    @Transactional
    public Category updateCategory(Long id, CategoryRequest request) {
        Category category = getOneCategory(id);

        categoryMapper.updateCategory(request, category);

        if (request.getParentId() != null && !category.getChildren().isEmpty()) {
            if (id.equals(request.getParentId())) {
                throw new ConflictException("Category is not found " + id);
            }
            Category parent = getOneCategory(request.getParentId());
            category.setParent(parent);
            category.setRoot(false);
        } else {
            category.setParent(null);
            category.setRoot(true);
        }

        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category find = getOneCategory(id);

        if (!find.getChildren().isEmpty()) {
            throw new ResourceNotFoundException("Category is not found") {
            };
        }

        categoryRepository.delete(find);
    }

}
