package com.store.product.service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.product.service.dto.ProductRequest;
import com.store.product.service.repository.ProductRepository;
import com.store.product.service.service.ProductService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestPropertySource(properties = "spring.sql.init.mode=never")
class ProductControllerTest {
    private static final String POSTGRES_IMAGE = "postgres:15.3";
    private static final String DATABASE_NAME = "products";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String API_URL = "/api/product";
    private static final String JSON_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(1500);
    private static final String PRODUCT_NAME = "Laptop";
    private static final String PRODUCT_DESCRIPTION = "Gaming Laptop";

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("Create a new product successfully")
    void createProduct_ValidRequest_ReturnsCreatedStatus() throws Exception {
        ProductRequest productRequest
                = new ProductRequest(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE);

        doNothing().when(productService).createProduct(any(ProductRequest.class));

        mockMvc.perform(post(API_URL)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create product with invalid request returns bad request")
    void createProduct_InvalidRequest_ReturnsBadRequest() throws Exception {
        ProductRequest invalidProductRequest
                = new ProductRequest("", PRODUCT_DESCRIPTION, PRODUCT_PRICE);
        mockMvc.perform(post(API_URL)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(invalidProductRequest)))
                .andExpect(status().isBadRequest());
    }
}
