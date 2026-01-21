package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTest;
import com.local_cart.controller.BrandController;
import com.local_cart.dto.request.BrandRequest;
import com.local_cart.entity.Country;
import com.local_cart.repository.BrandRepository;
import com.local_cart.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BrandControllerIT extends BaseIntegrationTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CountryRepository countryRepository;

    private Long countryId;

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();
        countryRepository.deleteAll();

        Country country = new Country();
        country.setName("Ukraine");
        countryId = countryRepository.save(country).getId();
    }

    @Test
    void createBrand_ShouldReturn201_WhenRequestIsValid() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Zewa");
        brandRequest.setCountryId(countryId);

        mockMvc.perform(post("/api/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Zewa")))
                .andExpect(jsonPath("$.countryBrand.name", is("Ukraine")));
    }
}
