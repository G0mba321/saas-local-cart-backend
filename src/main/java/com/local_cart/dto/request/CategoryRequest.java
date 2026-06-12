package com.local_cart.dto.request;

import com.local_cart.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "the request payload for crud on category")
public class CategoryRequest {

    @Schema(description = "enums for category types", example = "MEAT")
    @NotNull(message = "Cannot be null")
    private CategoryType baseType;

    @Schema(description = "display name for the category", example = "SuperMeatHenryBalls")
    @NotBlank(message = "Cannot be without name")
    private String name;

    @Schema(description = "this field needs for displaying hierarchy inheritance")
    private Long parentId;

    @Schema(description = "for understanding is it the main category or not for hierarchy inheritance")
    private boolean root = false;
}
