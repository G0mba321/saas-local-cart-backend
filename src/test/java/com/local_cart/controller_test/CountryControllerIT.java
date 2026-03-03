package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTest;
import com.local_cart.dto.request.CountryRequest;
import com.local_cart.repository.CountryRepository;
import io.restassured.http.ContentType;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.then;

@Sql(scripts = "classpath:sql/insertTestCountry.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/deleteTestCountry.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CountryControllerIT extends BaseIntegrationTest {

    private CountryRepository countryRepository;
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
    @DisplayName("get one country expected success response")
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

    @Test
    @DisplayName("get one country expected bad request if county not exist")
    void getOneCountry_ShouldReturn404_WhenRequestIsNotValid() {
        int id = 99;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("get all countries expected to be Ok if list is not empty")
    void getAllCountries_ShouldReturn201_WhenRequestIsValid() {
        int firstId = 100;
        int secondId = 101;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(2))
                .body("id", hasItems(firstId, secondId))
                .body("name", hasItems(notNullValue(), notNullValue()));

    }
    @Test
    @Sql(scripts = "classpath:sql/deleteTestCountry.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("get all countries expected to be OK if country list is empty")
    void getAllCountriesShouldReturn201_WhenListIsEmpty() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(0));
    }

    @Test
    @DisplayName("update country name expected to be OK if request is valid")
    void updateCountry_ShouldReturn201_WhenRequestIsValid() {
        int id = 100;

        CountryRequest request = new CountryRequest("Zimbabwe");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(id))
                .body("name", is(request.getName()));
    }

    @Test
    @DisplayName("update country expect to be Bad Request if country has no name")
    void updateCountry_ShouldReturn400_WhenRequestHasNoName() {
        int id = 100;

        CountryRequest request  = new CountryRequest("");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("update country expect to be Not Found if country has no id")
    void updateCountry_ShouldReturn404_WhenRequestNotFound() {
        int id = 99;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("delete country expect to be No Content if request is valid")
    void deleteCountry_ShouldReturn204_WhenRequestIsValid() {
        int id = 101;

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    @DisplayName("delete country expected to be Not Found if wrong id")
    void deleteCountry_ShouldReturn404_WhenIdIsNotOk() {
        int id = 99;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("detail", is("Country is not found " + id));
    }
}

