package com.store.streamsql;

import com.store.streamsql.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StreamSqlApplication {
	@SneakyThrows
    public static void main(String[] args) {
		System.out.println("Starting StreamSql Application");

		ConfigurableApplicationContext context
				= SpringApplication.run(StreamSqlApplication.class, args);

		ProductService productService = context.getBean(ProductService.class);

		productService.saveNewProductsIfDbIsEmpty();

	}

}
