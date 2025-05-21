package com.store.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceControllerTests {

    private static final String MYSQL_IMAGE = "mysql:8.3.0";
    private static final String DATABASE_NAME = "testdb";
    private static final String DATABASE_USERNAME = "user";
    private static final String DATABASE_PASSWORD = "password";

    private static final String BASE_URI = "http://localhost";
    private static final String API_PATH = "/api/order";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String ORDER_SKU_CODE = "iphone_15";
    private static final int ORDER_PRICE = 1000;
    private static final int ORDER_QUANTITY = 1;

    private static final String EXPECTED_RESPONSE = "Order Placed Successfully";

    private static final String SUBMIT_ORDER_JSON = """
        {
            "skuCode": "%s",
            "price": %d,
            "quantity": %d
        }
        """.formatted(ORDER_SKU_CODE, ORDER_PRICE, ORDER_QUANTITY);

    @Container
    static MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(MYSQL_IMAGE)
                    .withDatabaseName(DATABASE_NAME)
                    .withUsername(DATABASE_USERNAME)
                    .withPassword(DATABASE_PASSWORD);

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void registerMysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Submit order should return 201 and confirmation message")
    void shouldSubmitOrder_Return201AndConfirmationMessage() {

        var responseBodyString = RestAssured.given()
                .contentType(CONTENT_TYPE_JSON)
                .body(SUBMIT_ORDER_JSON)
                .when()
                .post(API_PATH)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        assertThat(responseBodyString, is(EXPECTED_RESPONSE));
    }
}
