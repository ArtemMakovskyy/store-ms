package com.store.inventory.service.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerTest {

    private static final String POSTGRES_IMAGE = "postgres:17";
    private static final String DATABASE_NAME = "inventorydb";
    private static final String DATABASE_USERNAME = "user";
    private static final String DATABASE_PASSWORD = "password";

    private static final String BASE_URI = "http://localhost";
    private static final String API_PATH = "/api/inventory";
    private static final String SKU_CODE = "iphone_15";

    private static final int QUANTITY_AVAILABLE = 1;
    private static final int QUANTITY_NOT_AVAILABLE = 1000;

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
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @DisplayName("Check inventory availability returns true when quantity is available")
    @Test
    void get_inventoryBySkuAndQuantity_ReturnsTrueIfAvailable() {
        var response = RestAssured.given()
                .when()
                .get(API_PATH + "?skuCode=" + SKU_CODE + "&quantity=" + QUANTITY_AVAILABLE)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);
        assertTrue(response);
    }

    @DisplayName("Check inventory availability returns false when quantity is not available")
    @Test
    void get_inventoryBySkuAndQuantity_ReturnsFalseIfNotAvailable() {
        var response = RestAssured.given()
                .when()
                .get(API_PATH + "?skuCode=" + SKU_CODE + "&quantity=" + QUANTITY_NOT_AVAILABLE)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);
        assertFalse(response);
    }
}
