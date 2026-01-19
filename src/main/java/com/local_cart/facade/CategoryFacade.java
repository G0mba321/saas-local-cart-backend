package com.local_cart.facade;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.entity.Category;
import com.local_cart.exceptions.ConflictException;
import com.local_cart.mapper.CategoryMapper;
import com.local_cart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CategoryFacade {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryService.createCategory(request);

        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        List<Category> roots = categoryService.findRoots();

        return roots.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getOneCategory(Long id) {
        Category find = categoryService.getOneCategory(id);

        return categoryMapper.toResponse(find);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryService.getOneCategory(id);

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryService.delete(id);
    }
}
