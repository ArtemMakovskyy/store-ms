package com.store.streamsql.stream.groupingby;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupingByService {

    private final ProductService productService;

    public void start() {

    }

    public Map<String, Double> averagePriceByCategory() {
        Map<String, Double> collect = productService.getProducts().stream()
                .collect(Collectors.groupingBy(ProductDto::getCategory, Collectors.averagingDouble(ProductDto::getPrice)));
        return collect;
    }

    public void groupingBy(){

        List<Sale> sales = List.of(
                new Sale("Електроніка", 1000),
                new Sale("Електроніка", 1500),
                new Sale("Одяг", 700),
                new Sale("Електроніка", 2500),
                new Sale("Одяг", 800)
        );


        Map<String, Integer> totalSalesByCategory = sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::getCategory,
                        Collectors.summingInt(Sale::getAmount)
                ));

        totalSalesByCategory.forEach((category, sum) -> System.out.println(category + ": " + sum));
    }


    @Getter
    @AllArgsConstructor
    private class Sale {
        private final String category;
        private final int amount;

    }
}

