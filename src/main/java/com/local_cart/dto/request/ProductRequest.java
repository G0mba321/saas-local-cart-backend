package com.local_cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private Integer inStock;

    @NotNull(message = "Cannot be without price")
    private Double price;

    private String description;

    private Map<String, String> extraDetails = new HashMap<>();

    private Long categoryId;

    private Long CountryId;

    private Long brandId;

}
