package com.store.streamsql.repository.isolations;

import com.store.streamsql.model.izolations.CustomerIzolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerIzolationRepository extends JpaRepository<CustomerIzolation, Long> {
}
