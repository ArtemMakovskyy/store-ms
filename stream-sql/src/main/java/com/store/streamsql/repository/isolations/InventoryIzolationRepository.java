package com.store.streamsql.repository.isolations;

import com.store.streamsql.model.izolations.InventoryIzolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryIzolationRepository extends JpaRepository<InventoryIzolation, Long> {
    InventoryIzolation findByProductId(Long productId);
}
