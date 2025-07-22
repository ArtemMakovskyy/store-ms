package com.store.streamsql.repository.customer;

import com.store.streamsql.model.customer.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCustomerRepository {

    private final EntityManager entityManager;

//    @PostConstruct
    public void init() {
        System.out.println("<<< CustomCustomerRepository init");
        List<Customer> customers = findAllCustomersWithOrders();
        customers.forEach(System.out::println);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> findAllCustomersWithOrders() {
        EntityGraph<Customer> graph =
                (EntityGraph<Customer>) entityManager.getEntityGraph("customers_with_orders");

        String jpql = "SELECT c FROM Customer c";
        return entityManager.createQuery(jpql, Customer.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getResultList();
    }
}
