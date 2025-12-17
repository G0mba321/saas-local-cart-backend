package com.local_cart.service.facade;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.entity.Category;
import com.local_cart.exceptions.ParentCategoryException;
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

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category toEntity = categoryMapper.toEntity(request);

        if (request.getParentId() != null) {
            Category parent = categoryService.findOne(request.getParentId());
            toEntity.setParent(parent);
            toEntity.setRoot(false);
        } else {
            toEntity.setRoot(true);
        }

        Category saved = categoryService.save(toEntity);

        return categoryMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        List<Category> roots = categoryService.findRoot();

        return roots.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getOneCategory(Long id) {
        Category find = categoryService.findOne(id);

        return categoryMapper.toResponse(find);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category findCategory = categoryService.findOne(id);

        categoryMapper.updateCategory(request, findCategory);

        if (request.getParentId() != null) {
            if (id.equals(request.getParentId())) {
                throw new ParentCategoryException();
            }
            Category parent = categoryService.findOne(request.getParentId());
            findCategory.setParent(parent);
            findCategory.setRoot(false);
        } else {
            findCategory.setParent(null);
            findCategory.setRoot(true);
        }

        Category updated = categoryService.save(findCategory);

        return categoryMapper.toResponse(updated);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryService.delete(id);
    }
}
