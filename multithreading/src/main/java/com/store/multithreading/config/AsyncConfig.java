package com.store.multithreading.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "dbTaskExecutor")
    public Executor taskExecutor() {
        int connectionsQuantityToDb = 16;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(connectionsQuantityToDb);
        executor.setMaxPoolSize(connectionsQuantityToDb * 2);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("DBAsync-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // выбросит исключение
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy()); // просто игнор
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy()); // выкидывает старую
        executor.initialize();
        return executor;
    }


    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Email-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "fileTaskExecutor")
    public Executor fileTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("File-");
        executor.initialize();
        return executor;
    }
}
