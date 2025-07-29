package com.store.multithreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync // or here or in config
public class MultithreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingApplication.class, args);
	}

}
