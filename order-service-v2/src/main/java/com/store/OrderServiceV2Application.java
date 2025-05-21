package com.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceV2Application {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceV2Application.class, args);
    }

}
