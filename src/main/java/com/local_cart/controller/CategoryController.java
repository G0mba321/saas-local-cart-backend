package com.local_cart.controller;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryResponse;
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
@Tag(name = "CategoryController",
        description = "Endpoints for crud operations on Category service")
public class CategoryController {

    private final CategoryFacade categoryFacade;

    @Operation(summary = "Create category")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid
                                                           @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get one category")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getOneCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoryFacade.getOneCategory(id));
    }

    @Operation(
            summary = "Get all categories",
            description = "Get a List of all categories"
    )
    @GetMapping
    public List<CategoryResponse> getAllCategoryRoot() {
        return categoryFacade.getCategoryRoot();
    }

    @Operation(summary = "Update one category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id, @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete one category")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.deleteCategory(id);
    }
}
