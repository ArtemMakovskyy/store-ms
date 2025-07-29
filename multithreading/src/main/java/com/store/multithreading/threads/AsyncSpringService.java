package com.store.multithreading.threads;

import com.store.multithreading.service.UserService;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AsyncSpringService {

    private final UserService userService;
    private final Random rand = new Random();

//    @PostConstruct
    public void init() {
        //  этот вариант не будет работать так как выполняется внутри класса и прокси не создастт   много пакетов
        startAsync(1);
        startAsync(2);
        startAsync(3);
    }

    @Async("dbTaskExecutor")
    public void startAsync(int threadNumber) {
        log.info(threadNumber + " > > > Starting async thread " + Thread.currentThread().getName());
        IntStream.range(1, rand.nextInt(100+10))
                .forEach(i -> userService.add(
                                userService.createFakeUser(threadNumber)
                        )
                );
        log.info(threadNumber + "<<< Ending async thread"  + Thread.currentThread().getName());
    }


    public void startAsync2() {
        IntStream.range(1, 1000)
                .forEach(i -> userService.add2(
                                userService.createFakeUser(i),i
                        )
                );
    }
}
