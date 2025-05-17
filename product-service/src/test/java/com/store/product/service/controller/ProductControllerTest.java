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

    @Container
    static PostgreSQLContainer<?> postgresContainer
            = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("productdb")
            .withUsername("user")
            .withPassword("password");

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
        ProductRequest productRequest = new ProductRequest("Guitar classic", "Guitar classic", BigDecimal.valueOf(1200));

        // given
        RestAssured.given()
                .contentType("application/json")
                .body(productRequest)

                // when
                .when()
                .post("/api/product")

                // then
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("get all Products Returns Produc tList")
    void get_allProducts_ReturnsProductList() {
        // given
        RestAssured.given()
                .contentType("application/json")

                // when
                .when()
                .get("/api/product")

                // then
                .then()
                .log().all()
                .statusCode(200)
                .body("[0].name", Matchers.equalTo("Guitar classic"))
                .body("[0].description", Matchers.equalTo("Guitar classic"))
                .body("[0].price", Matchers.equalTo(1200.0F));
    }
}
