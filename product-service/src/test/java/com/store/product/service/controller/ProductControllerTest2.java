package com.store.product.service.controller;

import com.store.product.service.dto.ProductRequest;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
class ProductControllerTest2 {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.3")
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
    void shouldCreateProductAndRetrieveIt() {

        ProductRequest productRequest = new ProductRequest("Guitar classic", "Guitar classic", BigDecimal.valueOf(1200));

        // POST
        RestAssured.given()
                .contentType("application/json")
                .body(productRequest)
                .when()
                .post("/api/product")
                .then()
                .log().all()
                .statusCode(201);

        // GET
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/product")
                .then()
                .log().all()
                .statusCode(200)
                .body("[0].name", Matchers.equalTo("Guitar classic"))
                .body("[0].description", Matchers.equalTo("Guitar classic"))
                .body("[0].price", Matchers.equalTo(1200.0F));
    }
}
