package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTestOld;
import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.entity.Category;
import com.local_cart.enums.CategoryType;
import com.local_cart.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CategoryControllerIT extends BaseIntegrationTestOld {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void createCategory_ShouldReturn201_WhenRequestIsValid() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Meat");
        categoryRequest.setBaseType(CategoryType.MEAT);

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Meat")))
                .andExpect(jsonPath("$.baseType", is("MEAT")));
    }

    @Test
    void createCategory_ShouldReturn201_WhenRootIsTrue() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Meat");
        categoryRequest.setBaseType(CategoryType.MEAT);
        categoryRequest.setRoot(true);
        categoryRequest.setParentId(null);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Meat")))
                .andExpect(jsonPath("$.root", is(true)));
    }

    @Test
    void createCategory_ShouldReturn201_WhenParentExist() throws Exception {
        Category main = new Category();
        main.setName("Main Meat");
        main.setBaseType(CategoryType.MEAT);
        main.setRoot(true);
        main = categoryRepository.save(main);

        CategoryRequest sub = new CategoryRequest();
        sub.setBaseType(CategoryType.MEAT);
        sub.setName("Sub Meat");
        sub.setRoot(false);
        sub.setParentId(main.getId());

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sub)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Sub Meat")))
                .andExpect(jsonPath("$.root", is(false)))
                .andExpect(jsonPath("$.parentId", is(main.getId().intValue())));
    }

    @Test
    void createCategory_ShouldReturn404_WhenParentNotExist() throws Exception {
        long badId = 999L;

        CategoryRequest sub = new CategoryRequest();
        sub.setBaseType(CategoryType.MEAT);
        sub.setName("Sub Meat");
        sub.setParentId(badId);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sub)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Category is not found " + badId)));
    }

    @Test
    void createCategory_ShouldReturn400_WhenNameIsEmpty() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("");
        categoryRequest.setBaseType(CategoryType.COFFEE);

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOneCategory_ShouldReturn200_WhenCategoryExist() throws Exception {
        Category category = new Category();
        category.setName("Meatball");
        category.setBaseType(CategoryType.MEAT);
        category.setRoot(true);
        category = categoryRepository.save(category);

        mockMvc.perform(get("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.name", is(category.getName())))
                .andExpect(jsonPath("$.root", is(true)));
    }

    @Test
    void getOneCategory_ShouldReturn404_WhenIdNotExist() throws Exception {
        long badId = 999L;

        mockMvc.perform(get("/api/category/{id}", badId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Category is not found " + badId)));
    }

    @Test
    void getAllCategoryRoot_ShouldReturn200_WhenListNotEmpty() throws Exception {
        Category category1 = new Category();
        category1.setName("Meatball");
        category1.setBaseType(CategoryType.MEAT);
        category1 = categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Bread");
        category2.setBaseType(CategoryType.BREAD);
        category2 = categoryRepository.save(category2);

        mockMvc.perform(get("/api/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(category1.getName())))
                .andExpect(jsonPath("$[1].name", is(category2.getName())));
    }

    @Test
    void getAllCategoryRoot_ShouldReturn200_WhenListIsEmpty() throws Exception {
        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void updateCategory_ShouldReturn200_WhenRequestIsValid() throws Exception {
        Category categoryOld = new Category();
        categoryOld.setName("Meatball");
        categoryOld.setBaseType(CategoryType.MEAT);
        categoryOld = categoryRepository.save(categoryOld);

        CategoryRequest categoryNew = new CategoryRequest();
        categoryNew.setName("Super MeatBall");
        categoryNew.setBaseType(CategoryType.MEAT);

        mockMvc.perform(put("/api/category/{id}", categoryOld.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryNew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryOld.getId().intValue())))
                .andExpect(jsonPath("$.name", is(categoryNew.getName())))
                .andExpect(jsonPath("$.baseType", is(categoryNew.getBaseType().name())));
    }

    @Test
    void updateCategory_ShouldReturn404_WhenRequestNotValid() throws Exception {
        long badId = 999L;

        CategoryRequest categoryNew = new CategoryRequest();
        categoryNew.setName("Super MeatBall");
        categoryNew.setBaseType(CategoryType.MEAT);

        mockMvc.perform(put("/api/category/{id}", badId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryNew)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Category is not found " + badId)));
    }

    @Test
    void deleteCategory_ShouldReturn204_WhenCategoryExist() throws Exception {
        Category category = new Category();
        category.setName("Super MeatBall");
        category.setBaseType(CategoryType.MEAT);
        category.setRoot(true);
        category.setParent(null);
        category = categoryRepository.save(category);

        mockMvc.perform(delete("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        boolean existCategory = categoryRepository.existsById(category.getId());
        assertThat(existCategory).isFalse();
    }

    @Test
    void deleteCategory_ShouldReturn404_WhenCategoryNotExist() throws Exception {
        long badId = 999L;

        mockMvc.perform(delete("/api/category/{id}", badId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Category is not found " + badId)));

    }
}
