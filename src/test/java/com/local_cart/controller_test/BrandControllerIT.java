package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTest;
import com.local_cart.dto.request.BrandRequest;
import com.local_cart.entity.Brand;
import com.local_cart.entity.Country;
import com.local_cart.repository.BrandRepository;
import com.local_cart.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void createBrand_ShouldReturn400_WhenNameIsNotValid() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("");
        brandRequest.setCountryId(countryId);

        mockMvc.perform(post("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBrand_ShouldReturn404_WhenCountryIsNotValid() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Pepsi");
        brandRequest.setCountryId(999L);

        mockMvc.perform(post("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOneBrand_ShouldReturn200_WhenIdExists() throws Exception {
        Country country = countryRepository.findById(countryId).get();

        Brand brand = new Brand();
        brand.setName("Paulainer");
        brand.setCountryBrand(country);
        brandRepository.save(brand);

        mockMvc.perform(get("/api/brand/{id}", brand.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(brand.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Paulainer")))
                .andExpect(jsonPath("$.countryBrand.name", is("Ukraine")));
    }

    @Test
    void getOneBrand_ShouldReturn404_WhenIdNotExists() throws Exception {
        long brandNotExistId = 999L;

        mockMvc.perform(get("/api/brand/{id}", brandNotExistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Brand is not found " + brandNotExistId)));
    }

    @Test
    void getAllBrands_ShouldReturnListAnd200_WhenBrandsExists() throws Exception {
        Country ukraine = countryRepository.findById(countryId).get();

        Brand ukrBrand = new Brand();
        ukrBrand.setName("Ukr Brand");
        ukrBrand.setCountryBrand(ukraine);
        brandRepository.save(ukrBrand);

        Brand ukrBrand2 = new Brand();
        ukrBrand2.setName("Ukr2 Brand");
        ukrBrand2.setCountryBrand(ukraine);
        brandRepository.save(ukrBrand2);

        mockMvc.perform(get("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ukr Brand")))
                .andExpect(jsonPath("$[0].countryBrand.name", is("Ukraine")))
                .andExpect(jsonPath("$[1].name", is("Ukr2 Brand")))
                .andExpect(jsonPath("$[1].countryBrand.name", is("Ukraine")));

    }

    @Test
    void getAllBrands_ShouldReturnEmptyListAnd200_WhenNoBrandsExist() throws Exception {
        mockMvc.perform(get("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void updateBrand_ShouldReturn200_WhenBrandExist() throws Exception {
        Country country = countryRepository.findById(countryId).get();

        Brand oldBrand = new Brand();
        oldBrand.setName("Old BrandName");
        oldBrand.setCountryBrand(country);
        brandRepository.save(oldBrand);

        BrandRequest newBrandRequest = new BrandRequest();
        newBrandRequest.setName("New BrandName");
        newBrandRequest.setCountryId(countryId);

        mockMvc.perform(put("/api/brand/{id}", oldBrand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBrandRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(oldBrand.getId().intValue())))
                .andExpect(jsonPath("$.name", is("New BrandName")))
                .andExpect(jsonPath("$.countryBrand.name", is("Ukraine")));
    }

    @Test
    void updateBrand_ShouldReturn404_WhenBrandNotExist() throws Exception {
        long badId = 9999L;

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("not existBrand");
        brandRequest.setCountryId(countryId);

        mockMvc.perform(put("/api/brand/{id}", badId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Brand is not found " + badId)));

    }

    @Test
    void deleteBrand_ShouldReturn204_WhenBrandExists() throws Exception {
        Country country = countryRepository.findById(countryId).get();

        Brand brand = new Brand();
        brand.setName("Brand toDelete");
        brand.setCountryBrand(country);
        brandRepository.save(brand);

        mockMvc.perform(delete("/api/brand/{id}", brand.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        boolean brandExist = brandRepository.existsById(brand.getId());
        assertThat(brandExist).isFalse();
    }

    @Test
    void deleteBrand_ShouldReturn404_WhenBrandNotExists() throws Exception {
        long badId = 999L;

        mockMvc.perform(delete("/api/brand/{id}", badId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Brand is not found " + badId)));

    }
}
