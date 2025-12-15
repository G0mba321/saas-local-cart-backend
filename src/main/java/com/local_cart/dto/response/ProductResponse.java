package com.local_cart.dto.response;

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
public class ProductResponse {
    private Long id;

    private String name;

    private Integer inStock;

    private Double price;

    private String description;

    private Map<String, String> extraDetails = new HashMap<>();

    private CategoryResponse category;

    private CountryResponse originCountry;

    private BrandResponse brand;
}
