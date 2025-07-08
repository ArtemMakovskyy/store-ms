package com.store.streamsql.stream;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import com.store.streamsql.util.Util;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamOf {
    private final ProductService productService;

    public void start() {
        process();

    }

    public void process2() {
        Util.line____________();
        List<String> numbersAsString = Arrays.asList("1", "2", "3", "4", "5");

        int sum = numbersAsString.stream()
                .mapToInt(num -> Integer.parseInt(num))
                .sum();

        System.out.println("Сума: " + sum);

        Util.line____________();
        Stream<Long> powersOfTwo = Stream.iterate(1L, n -> n * 2);
    }

    public void process() {
        Util.line____________();
        Stream.of("1", "2", "3")
                .map(Integer::parseInt)
                .map(n -> n + 1)
                .forEach(System.out::println);

        List<Integer> list = Stream.of(1, 2, 3)
                .map(n -> n * 2)
                .toList();

        String joinedNumbers = Stream.of(5, 10, 15)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "{", "}"));
        // joinedNumbers: "{5-10-15}"

        List<Integer> numberSequence = Stream.iterate(0, i -> i + 1)
                .limit(5)
                .collect(Collectors.toList());

        // numberSequence: [0, 1, 2, 3, 4]

        int sum = IntStream.of(1, 2, 3, 4, 5).sum();

        OptionalDouble average = IntStream.of(1, 2, 3, 4, 5).average();

        IntStream.rangeClosed(1, 10)
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);

        List<ProductDto> products = productService.getProducts();
        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> products.get(i - 1))
                .forEach(System.out::println);

    }


}
