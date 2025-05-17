package com.store.product.service.controller;

import com.store.product.service.dto.ProductRequest;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final String POSTGRES_IMAGE = "postgres:17";
    private static final String DATABASE_NAME = "productdb";
    private static final String DATABASE_USERNAME = "user";
    private static final String DATABASE_PASSWORD = "password";

    private static final String BASE_PATH = "/api/product";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String TEST_PRODUCT_NAME = "Guitar classic";
    private static final String TEST_PRODUCT_DESCRIPTION = "Guitar classic";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.valueOf(1200);
    private static final float TEST_PRODUCT_PRICE_FLOAT = 1200.0F;

    @Container
    static PostgreSQLContainer<?> postgresContainer
            = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USERNAME)
            .withPassword(DATABASE_PASSWORD);

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("add valid ProductRequest Return 201 Created")
    void add_validProductRequest_Return201Created() {
        ProductRequest productRequest = new ProductRequest(TEST_PRODUCT_NAME, TEST_PRODUCT_DESCRIPTION, TEST_PRODUCT_PRICE);

        RestAssured.given()
                .contentType(CONTENT_TYPE_JSON)
                .body(productRequest)
                .when()
                .post(BASE_PATH)
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("get all Products Returns ProductList")
    void get_allProducts_ReturnsProductList() {
        RestAssured.given()
                .contentType(CONTENT_TYPE_JSON)
                .when()
                .get(BASE_PATH)
                .then()
                .log().all()
                .statusCode(200)
                .body("[0].name", Matchers.equalTo(TEST_PRODUCT_NAME))
                .body("[0].description", Matchers.equalTo(TEST_PRODUCT_DESCRIPTION))
                .body("[0].price", Matchers.equalTo(TEST_PRODUCT_PRICE_FLOAT));
    }
}
