package com.store.order.service.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceApplicationTests {

//    @ServiceConnection
//    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0")
//            .withDatabaseName("test")
//            .withUsername("test")
//            .withPassword("test");
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeAll
//    static void startContainer() {
//        mySQLContainer.start();
//        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
//        System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
//        System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
//    }
//
//    @BeforeEach
//    void setUpRestAssured() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = port;
//    }
//
//    @Test
//    void shouldSubmitOrder() {
//        String submitOrderJson = """
//                {
//                    "skuCode": "iphone_15",
//                    "price": 1000,
//                    "quantity": 1
//                }
//                """;
//
//        var responseBodyString = RestAssured.given()
//                .contentType("application/json")
//                .body(submitOrderJson)
//                .when()
//                .post("/api/order")
//                .then()
//                .log().all()
//                .statusCode(201)
//                .extract()
//                .body()
//                .asString();
//
//        assertThat(responseBodyString, is("Order Placed Successfully"));
//    }
}
