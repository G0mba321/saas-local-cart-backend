package com.local_cart.dto.request;

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
public class ProductRequest {

    @NotBlank(message = "Product cannot be without name")
    private String name;

    @PositiveOrZero
    private Integer inStock;

    @NotNull(message = "Cannot be without price")
    @PositiveOrZero
    private Double price;

    private String description;

    private Map<String, String> extraDetails = new HashMap<>();

    private Long categoryId;

    private Long countryId;

    private Long brandId;

}
