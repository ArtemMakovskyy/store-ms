package com.store.streamsql.service;

import com.github.javafaker.Faker;
import com.store.streamsql.model.Customer;
import com.store.streamsql.model.CustomerOrder;
import com.store.streamsql.model.OrderStatus;
import com.store.streamsql.repository.CustomerOrderRepository;
import com.store.streamsql.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DataInitService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository orderRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        if(customerRepository.count() == 0) {
            List<Customer> customers = generateCustomers(1000);
            customerRepository.saveAll(customers);

            List<CustomerOrder> orders = generateOrdersForCustomers(customers);
            orderRepository.saveAll(orders);
        }
    }

    private List<Customer> generateCustomers(int count) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = Customer.builder()
                    .fullName(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .registeredAt(randomDate(2010, 2024))
                    .build();
            customers.add(customer);
        }
        return customers;
    }

    private List<CustomerOrder> generateOrdersForCustomers(List<Customer> customers) {
        List<CustomerOrder> orders = new ArrayList<>();
        for (Customer customer : customers) {
            int ordersCount = random.nextInt(10) + 1;
            for (int i = 0; i < ordersCount; i++) {
                CustomerOrder order = CustomerOrder.builder()
                        .customer(customer)
                        .orderDate(randomDate(2015, 2025))
                        .status(randomStatus())
                        .build();
                orders.add(order);
            }
        }
        return orders;
    }

    private LocalDate randomDate(int startYear, int endYear) {
        int year = startYear + random.nextInt(endYear - startYear + 1);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        return LocalDate.of(year, month, day);
    }

    private OrderStatus randomStatus() {
        OrderStatus[] statuses = OrderStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }
}
