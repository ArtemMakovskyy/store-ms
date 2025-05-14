package com.store.product.service.util;

import com.store.product.service.model.Product;
import com.store.product.service.repository.ProductRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() < 1) {
            Product product = new Product();
            product.setName("Classical guitar ADMIRA A20");
            product.setDescription("Enrique Keller SA factory, located in the northern part of Spain.");
            product.setPrice(BigDecimal.valueOf(54156));

            productRepository.save(product);
        }
    }
}
