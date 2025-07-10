package com.store.streamsql.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String category;
    private double price;
    private boolean inStock;
    private List<String> tags;
    private LocalDate addedDate;
}
