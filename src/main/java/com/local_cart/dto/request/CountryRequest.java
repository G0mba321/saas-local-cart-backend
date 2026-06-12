package com.local_cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "the request payload for crud on country")
public class CountryRequest {

    @Schema(description = "display name of country,", example = "Ukraine")
    @NotBlank(message = "Country cannot be without name")
    private String name;
}
