package com.store.order.service.service;

import com.store.order.service.client.InventoryRestClient;
import com.store.order.service.dto.OrderRequest;
import com.store.order.service.model.Order;
import com.store.order.service.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRestClient inventoryRestClient;

    public void placeOrder(OrderRequest orderRequest) {
        boolean inStock = inventoryRestClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (inStock) {
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
        } else {
            log.error("Product with SKU code {} is not in stock", orderRequest.skuCode());
            throw new RuntimeException("Product with Skucode " + orderRequest.skuCode() + "is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}
