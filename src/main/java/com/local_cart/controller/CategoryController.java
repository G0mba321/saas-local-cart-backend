package com.local_cart.controller;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryChildrenResponse;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.entity.Category;
import com.local_cart.facade.CategoryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
@Tag(name = "category-controller",
        description = "endpoints for crud operations on category service")
public class CategoryController {

    private final CategoryFacade categoryFacade;

    @Operation(summary = "create category")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid
                                                           @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "get one category")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryChildrenResponse> getOneCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoryFacade.getOneCategory(id));
    }

    @Operation(
            summary = "get all root categories",
            description = "get a list of all root categories"
    )
    @GetMapping
    public List<CategoryChildrenResponse> getAllCategoryRoot() {
        return categoryFacade.getCategoryRoot();
    }

    @Operation(
            summary = "get all children categories",
            description = "get a list of all children categories"
    )
    @GetMapping("/{id}/children")
    public List<CategoryResponse> getAllChildrenCategories(@PathVariable Long id) {
        return categoryFacade.getAllChildrenCategories(id);
    }

    @Operation(summary = "update one category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id, @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "delete one category")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.deleteCategory(id);
    }
}
