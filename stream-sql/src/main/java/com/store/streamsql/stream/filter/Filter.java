package com.store.streamsql.stream.filter;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import com.store.streamsql.util.Util;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Filter {
    private final ProductService productService;

    public void start() {
//        voidAllAnyMatch();
//        distinctFlatmap();
//        maxMinPrice();
//        filterInStock();
    }

    private void voidAllAnyMatch() {
        Util.line____________();
        boolean is = productService.getProducts().stream()
                .anyMatch(dto -> dto.getTags().contains("asian"));

        Util.line____________();
        List<String> filterTags = List.of("asian", "spicy");

//        productService.getProducts().stream()
//                .filter(dto -> dto.getTags().stream().anyMatch(filterTags::contains))
//                .forEach(System.out::println);

    }

    private void distinctFlatmap() {
        Util.line____________();
        productService.getProducts().stream()
                .flatMap(s -> s.getTags().stream())
                .distinct()
                .sorted()
                .limit(5)
                .forEach(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .map(ProductDto::getCategory)
                .distinct()
                .limit(5)
                .forEach(System.out::println);
    }

    private void filterInStock() {
        Util.line____________();
        productService.getProducts().stream()
                .filter(ProductDto::isInStock)
                .limit(5)
                .forEach(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .filter(pdto -> pdto.isInStock())
                .limit(5)
                .forEach(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .filter(product -> product.getPrice() < 100 && product.getCategory().equalsIgnoreCase("grocery"))
                .limit(5)
                .forEach(System.out::println);

    }

    private void maxMinPrice() {
        Util.line____________();
        productService.getProducts().stream()
                .sorted(
//                        Comparator.comparingDouble(product -> product.getPrice())  // do not work
                        Comparator.comparingDouble(ProductDto::getPrice).reversed()
                )
                .limit(2)
                .forEach(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .sorted(Comparator.comparingDouble(product -> product.getPrice()))
                .limit(2)
                .forEach(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .max(Comparator.comparingDouble(product -> product.getPrice()))
                .ifPresent(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .max(Comparator.comparingDouble(ProductDto::getPrice))
                .ifPresent(System.out::println);

        Util.line____________();
        productService.getProducts().stream()
                .min(Comparator.comparingDouble(ProductDto::getPrice))
                .ifPresent(System.out::println);
    }


}
