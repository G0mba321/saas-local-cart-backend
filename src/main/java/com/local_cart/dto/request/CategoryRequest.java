package com.local_cart.dto.request;

import com.local_cart.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {

    @NotNull(message = "Cannot be null")
    private CategoryType baseType;

    @NotBlank(message = "Cannot be without name")
    private String name;

    private Long parentId;

    private boolean root = false;
}
