package com.store.streamsql.repository.isolations;

import com.store.streamsql.model.izolations.OrderIzolation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderIzolationRepository extends JpaRepository<OrderIzolation, Long> {
    List<OrderIzolation> findByCustomerId(Long customerId);
}
