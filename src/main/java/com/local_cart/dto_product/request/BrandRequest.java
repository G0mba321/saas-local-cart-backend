package com.local_cart.dto_product.request;

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
public class BrandRequest {

    @NotBlank(message = "Brand name cannot be empty")
    private String name;

    @NotNull(message = "Required country name")
    private String countryName;
}
