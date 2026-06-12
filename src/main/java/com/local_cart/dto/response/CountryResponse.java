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
@Schema(description = "the response payload for saving country details")
public class CountryResponse {

    @Schema(description = "unique id for the country,", example = "38", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "display name of country", example = "Ukraine")
    private String name;
}
