package com.local_cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "the response payload for saving product details")
public class ProductResponse {

    @Schema(description = "unique id for the category", example = "555", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "display name of product", example = "Camel")
    private String name;

    @Schema(description = "how many products in stock", example = "1488")
    private Integer inStock;

    @Schema(description = "price of product", example = "175.00")
    private Double price;

    @Schema(description = "description of product",
            example = "This product is shit")
    private String description;

    @Schema(description = "extra details for products")
    private Map<String, String> extraDetails = new HashMap<>();

    @Schema(description = "id of category product", accessMode = Schema.AccessMode.READ_ONLY)
    private CategoryResponse category;

    @Schema(description = "id of country product", accessMode = Schema.AccessMode.READ_ONLY)
    private CountryResponse originCountry;

    @Schema(description = "id of brand product", accessMode = Schema.AccessMode.READ_ONLY)
    private BrandResponse brand;
}
