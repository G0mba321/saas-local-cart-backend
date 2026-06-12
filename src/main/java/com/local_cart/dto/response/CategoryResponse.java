package com.local_cart.dto.response;

import com.local_cart.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "the response payload for saving category details")
public class CategoryResponse {

    @Schema(description = "unique id for the category", example = "167" , accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "enums for category types,", example = "MEAT")
    private CategoryType baseType;

    @Schema(description = "for understanding is it the main category or not for hierarchy inheritance")
    private boolean root = false;

    @Schema(description = "display name for the category = SuperMeatHenryBalls")
    private String name;

    @Schema(description = "this field needs for displaying hierarchy inheritance", accessMode = Schema.AccessMode.READ_ONLY)
    private Long parentId;

}
