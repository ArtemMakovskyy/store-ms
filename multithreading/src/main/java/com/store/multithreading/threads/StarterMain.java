package com.store.multithreading.threads;

import jakarta.annotation.PostConstruct;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StarterMain {
    private final AsyncSpringService asyncSpringService;

    @PostConstruct
    public void init() {
//        IntStream.range(1, 100).forEach(asyncSpringService::startAsync);

        asyncSpringService.startAsync2();

    }
}
