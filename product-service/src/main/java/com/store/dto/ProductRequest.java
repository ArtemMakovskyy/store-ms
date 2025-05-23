package com.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be empty")
        String name,

        @NotBlank(message = "Product description cannot be empty")
        String description,

        @Positive(message = "Price must be greater than zero")
        BigDecimal price
) {
}
