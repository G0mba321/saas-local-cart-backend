package com.local_cart.controller;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.facade.CategoryFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryFacade categoryFacade;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid
                                                           @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getOneCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoryFacade.getOneCategory(id));
    }

    @GetMapping
    public List<CategoryResponse> getAllCategoryRoot() {
        return categoryFacade.getCategoryRoot();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id, @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryFacade.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.deleteCategory(id);
    }
}
