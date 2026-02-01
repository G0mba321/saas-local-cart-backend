package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTest;
import com.local_cart.dto.request.CountryRequest;
import io.restassured.http.ContentType;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Sql(scripts = "classpath:sql/insertTestCountry.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/deleteTestCountry.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CountryControllerIT extends BaseIntegrationTest {

    private final String BASE_URI = "/api/country";

    @Test
    @DisplayName("create country expected success response")
    void createCountry_ShouldReturn201_WhenRequestIsValid() {
        CountryRequest request = new CountryRequest("IRAN");

        given()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URI)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("name", is(request.getName()));
    }

    @Test
    @DisplayName("create country expected unsuccessful response if no country name")
    void createCountry_ShouldReturn400_WhenRequestIsNotValid() {
        CountryRequest request = new CountryRequest("");

        given()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URI)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void getOneCountry_ShouldReturn200_WhenRequestIsValid() {
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(id))
                .body("name", notNullValue());

    }

}
