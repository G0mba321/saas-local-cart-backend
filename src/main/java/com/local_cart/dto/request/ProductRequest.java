package com.local_cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "the request payload for crud on product")
public class ProductRequest {

    @Schema(description = "display name of product", example = "Camel")
    @NotBlank(message = "Product cannot be without name")
    private String name;

    @Schema(description = "how many products in stock", example = "1488")
    @PositiveOrZero
    private Integer inStock;

    @Schema(description = "price of product", example = "175.00")
    @NotNull(message = "Cannot be without price")
    @PositiveOrZero
    private Double price;

    @Schema(description = "description of product",
            example = "This product is shit")
    private String description;

    @Schema(description = "extra details for products")
    private Map<String, String> extraDetails = new HashMap<>();

    @Schema(description = "id of category product")
    private Long categoryId;

    @Schema(description = "id of country product")
    private Long countryId;

    @Schema(description = "id of brand product")
    private Long brandId;

}
