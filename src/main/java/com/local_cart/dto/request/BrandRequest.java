package com.local_cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "the request payload for crud on brand")
public class BrandRequest {

    @Schema(description = "display name of brand", example = "Camel")
    @NotBlank(message = "Brand name cannot be empty")
    private String name;

    @Schema(description = "brand cannot be without country due to country of origin")
    @NotNull(message = "Required country id")
    private Long countryId;
}
