package com.store.streamsql.stream.map;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapStream {
    private final ProductService productService;

    public void start() {
        List<ProductDto> products = productService.getProducts();

        groupByCategory(products);
        countByCategory(products);
        averagePriceByCategory(products);
        mapByName(products);
        namesGroupedByCategory(products);
        mostExpensiveByCategory(products);
        groupByAddedDate(products);
    }


    public Map<String, List<ProductDto>> groupByCategory(List<ProductDto> products) {
        return products.stream()
                .collect(Collectors.groupingBy(ProductDto::getCategory));
    }

    /**
     * 2. Подсчет продуктов по категории: Map<Категория, Кол-во>
     */
    public Map<String, Long> countByCategory(List<ProductDto> products) {
        return products.stream()
                .collect(
                        Collectors.groupingBy
                                (
                                        ProductDto::getCategory,
                                        Collectors.counting()
                                )
                );
    }

    /**
     * 3. Средняя цена по категории: Map<Категория, СредняяЦена>
     */
    public Map<String, Double> averagePriceByCategory(List<ProductDto> products) {
        return products.stream()
                .collect(
                        Collectors.groupingBy
                                (
                                        ProductDto::getCategory,
                                        Collectors.averagingDouble(ProductDto::getPrice)
                                )
                );
    }

    /**
     * 4. Map<ИмяПродукта, Продукт>
     * Если имена уникальны.
     */
    public Map<String, ProductDto> mapByName(List<ProductDto> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        ProductDto::getName,
                        p -> p
                ));
    }

    /**
     * 5. Map<Категория, Список Названий Продуктов>
     */
    public Map<String, List<String>> namesGroupedByCategory(List<ProductDto> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductDto::getCategory,
                        Collectors.mapping(ProductDto::getName, Collectors.toList())
                ));
    }

    /**
     * 6. Map<Категория, Самый дорогой продукт в категории>
     */
    public Map<String, Optional<ProductDto>> mostExpensiveByCategory(List<ProductDto> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductDto::getCategory,
                        Collectors.maxBy(Comparator.comparingDouble(ProductDto::getPrice))
                ));
    }

    /**
     * 7. Map<ДатаДобавления, Список Продуктов>
     */
    public Map<LocalDate, List<ProductDto>> groupByAddedDate(List<ProductDto> products) {
        return products.stream()
                .collect(Collectors.groupingBy(ProductDto::getAddedDate));
    }

}
