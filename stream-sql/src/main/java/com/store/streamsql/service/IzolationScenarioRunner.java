package com.store.streamsql.service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IzolationScenarioRunner {

    private final IzolationDemoService izolationDemoService;

    public void runReadUncommittedDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // T1: обновление
        executor.submit(() -> izolationDemoService.updateQuantity(1L, 42));

        // Задержка, чтобы T1 начал транзакцию, но не завершил
        sleep(1000);

        // T2: чтение пока T1 ещё в процессе
        executor.submit(() -> izolationDemoService.readQuantity(1L));

        executor.shutdown();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
