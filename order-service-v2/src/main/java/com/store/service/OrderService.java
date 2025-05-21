package com.store.service;

import com.store.client.InventoryRestClient;
import com.store.dto.OrderRequest;
import com.store.event.OrderPlacedEvent;
import com.store.model.Order;
import com.store.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRestClient inventoryRestClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        boolean inStock = inventoryRestClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (inStock) {
            Order order = mapToOrder(orderRequest);
            orderRepository.save(order);

            sendKafkaTopic(order.getOrderNumber(), orderRequest);
        } else {
            log.error("Product with SKU code {} is not in stock", orderRequest.skuCode());
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + "is not in stock");
        }
    }

    private void sendKafkaTopic(String orderNumber, OrderRequest orderRequest) {
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(orderNumber);
        orderPlacedEvent.setEmail(orderRequest.userDetails().email());
        orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
        orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
        log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
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
