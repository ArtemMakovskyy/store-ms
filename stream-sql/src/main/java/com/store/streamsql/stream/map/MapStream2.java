package com.store.streamsql.stream.map;

import com.store.streamsql.dto.ProductDto;
import com.store.streamsql.service.ProductService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapStream2 {

    public void start() {
        map1();
    }


    private void map1() {
        List<User> users = List.of(
                new User("John Doe", 30),
                new User("Jane Doe", 25)
        );

        Map<String, Integer> usersMap = users.stream()
                .collect(Collectors.toMap(
                        User::getName,
                        User::getAge
                ));

        usersMap.forEach((name, age) -> System.out.println(name + ": " + age));

    }


    @Getter
    @AllArgsConstructor
    private class User {
        private final String name;
        private final int age;
    }
}
