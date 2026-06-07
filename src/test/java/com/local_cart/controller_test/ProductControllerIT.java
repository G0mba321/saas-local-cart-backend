package com.local_cart.controller_test;

import com.local_cart.BaseIntegrationTest;
import com.local_cart.dto.request.ProductRequest;
import io.restassured.http.ContentType;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Sql(scripts = {"classpath:sql/insertTestCountry.sql",
        "classpath:sql/insertTestBrand.sql",
        "classpath:sql/insertTestCategory.sql",
        "classpath:sql/insertTestProduct.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:sql/deleteTestProduct.sql",
        "classpath:sql/deleteTestBrand.sql",
        "classpath:sql/deleteTestCategory.sql",
        "classpath:sql/deleteTestCountry.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductControllerIT extends BaseIntegrationTest {

    private final String BASE_URI = "/api/product";

    private static @NonNull ProductRequest getProductRequest() {
        Map<String, String> details = new HashMap<>();
        details.put("volume", "0.5:");
        details.put("style", "Lagger");
        details.put("alcohol", "0.5%");

        ProductRequest request = new ProductRequest();
        request.setName("Opillya");
        request.setInStock(100);
        request.setPrice(24.99);
        request.setDescription("vey cool beer hell yeah");
        request.setExtraDetails(details);
        request.setCategoryId(100L);
        request.setCountryId(100L);
        request.setBrandId(100L);
        return request;
    }

    @Test
    @DisplayName("create product expected success response")
    void createProduct_ShouldReturn201_WhenRequestIsValid() {
        ProductRequest request = getProductRequest();

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
                .body("name", is(request.getName()))
                .body("price", is(request.getPrice().floatValue()))
                .body("category.id", is(request.getCategoryId().intValue()))
                .body("originCountry.id", is(request.getCountryId().intValue()))
                .body("brand.id", is(request.getBrandId().intValue()));
    }

    @Test
    @DisplayName("create product should expect unsuccess response when name not exist")
    void createProduct_ShouldReturn400_WhenNameIsNotValid() {
        ProductRequest request = getProductRequest();

        request.setName("");

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
    @DisplayName("create product should expect unsuccess response when price not exist")
    void createProduct_ShouldReturn400_WhenPriceIsNotValid() {
        ProductRequest request = getProductRequest();

        request.setPrice(null);

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
    @DisplayName("create product should expect unsuccess response when price is negative")
    void createProduct_ShouldReturn400_WhenPriceIsNegative() {
        ProductRequest request = getProductRequest();

        request.setPrice(-24.99);

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
    @DisplayName("get One Product expect success response")
    void getOneProduct_ShouldReturn200_WhenRequestIsValid() {
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(id));
    }

    @Test
    @DisplayName("get One Product expect unsuccess response if no ID")
    void getOneProduct_ShouldReturn404_WhenIdIsNotValid() {
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
    @DisplayName("get all product expect success response is list is not empty")
    void getAllProducts_ShouldReturn200_WhenRequestIsValid() {
        int id1 = 100;
        int id2 = 101;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(2))
                .body("id", hasItems(id1, id2))
                .body("name", hasItems(notNullValue(), notNullValue()));
    }

    @Test
    @Sql(scripts = {"classpath:sql/deleteTestProduct.sql",
            "classpath:sql/deleteTestBrand.sql",
            "classpath:sql/deleteTestCategory.sql",
            "classpath:sql/deleteTestCountry.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("get all product expect success response if list is empty")
    void getAllProducts_ShouldReturn201_WhenListIsEmpty() {

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
    @DisplayName("update product should return 201 if request is valid")
    void updateProduct_ShouldReturn201_WhenRequestIsValid() {
        ProductRequest request = new ProductRequest();

        request.setName("Budd");
        request.setPrice(30.00);
        request.setInStock(2);
        request.setCountryId(101L);
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(id));
    }

    @Test
    @DisplayName("update product should return 400 when name is not valid")
    void updateProduct_ShouldReturn400_WhenNameIsNotValid() {
        ProductRequest request = new ProductRequest();

        request.setName("");
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("update product should return 404 when id is not valid")
    void updateProduct_ShouldReturn404_WhenIdIsNotValid() {
        ProductRequest request = new ProductRequest();
        request.setName("Opillya");
        request.setInStock(100);
        request.setPrice(24.99);
        request.setDescription("vey cool beer hell yeah");
        request.setCategoryId(100L);
        request.setCountryId(100L);
        request.setBrandId(100L);
        int id = 99;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("update product should return 400 when inStock is negative number")
    void updateProduct_ShouldReturn400_WhenInStockIsNegative() {
        ProductRequest request = new ProductRequest();
        request.setInStock(-100);
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("update product should return 400 when price is negative number")
    void updateProduct_ShouldReturn400_WhenPriceIsNegative() {
        ProductRequest request = new ProductRequest();
        request.setPrice(-100.00);
        int id = 100;

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("delete product should return 204 when successfully deleted")
    void deleteProduct_ShouldReturn204_WhenProductDeleted() {
        int id = 100;

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
    @DisplayName("delete product should return 404 when product not found")
    void deleteProduct_ShouldReturn404_WhenProductNotFound() {
        int id = 99;

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/{id}", id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("detail", is("Product is not found " + id));
    }
}
