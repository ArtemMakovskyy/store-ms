package com.store.streamsql.repository.customer;

import com.store.streamsql.model.customer.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
