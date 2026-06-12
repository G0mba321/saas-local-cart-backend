package com.local_cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "the response payload for saving children category details")
public class CategoryChildrenResponse extends CategoryResponse {
    @Schema(description = "getting children category")
    private List<CategoryResponse> children;

}
