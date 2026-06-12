package com.local_cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "the response payload for saving brand details")
public class BrandResponse {

    @Schema(description = "unique id for the brand", example = "1488", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "display name of brand", example = "Camel")
    private String name;

    @Schema(description = "field for retrieving country id and name from CountryResponse class",
            accessMode = Schema.AccessMode.READ_ONLY)
    private CountryResponse countryBrand;
}
