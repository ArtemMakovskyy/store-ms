package com.store.repository;

import com.store.model.Product;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

@Observed
public interface ProductRepository extends JpaRepository<Product,Long> {
}
