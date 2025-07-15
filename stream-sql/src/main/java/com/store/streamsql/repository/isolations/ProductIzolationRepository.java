package com.store.streamsql.repository.isolations;

import com.store.streamsql.model.izolations.ProductIzolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIzolationRepository extends JpaRepository<ProductIzolation, Long> {
}
