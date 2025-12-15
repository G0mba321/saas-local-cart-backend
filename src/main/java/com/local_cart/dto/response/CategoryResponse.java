package com.local_cart.dto.response;

import com.local_cart.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private CategoryType baseType;
    private boolean isRoot = false;
    private String name;

}
