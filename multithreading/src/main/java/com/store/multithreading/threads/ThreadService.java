package com.store.multithreading.threads;

import com.store.multithreading.service.UserService;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadService extends Thread {
        private final UserService userService;

        public void task(){
                IntStream.range(0,100)
                        .forEach(userService::createFakeUser);

        }
}
