package com.store.order.service.service;

import com.store.order.service.client.InventoryClient;
import com.store.order.service.dto.InventoryResponse;
import com.store.order.service.dto.OrderLineItemsDto;
import com.store.order.service.dto.OrderRequest;
import com.store.order.service.model.Order;
import com.store.order.service.model.OrderLineItems;
import com.store.order.service.repository.OrderRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public String placeOrder(OrderRequest orderRequest) {
        //todo remove excess
        log.info("orderRequest\n" + orderRequest);
        Order order = createOrder(orderRequest);
        log.info("order\n" + order);
        List<String> skuCodesFromOrderLineItems = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        List<InventoryResponse> inventoryResponses
                = inventoryClient.inventoriesResponseBySkuCodes(skuCodesFromOrderLineItems);
        log.info("inventoryResponses\n" + inventoryResponses);
        if (areAllSkuCodesInStock(inventoryResponses, skuCodesFromOrderLineItems)) {
            orderRepository.save(order);
            //todo subtract quantity from warehouse if successful
            return "Order placed successfully";
        }
        throw new IllegalArgumentException("Product is not in stock");
    }

    public boolean areAllSkuCodesInStock(
            List<InventoryResponse> inventoryResponses, List<String> skuCodesOfOrderLineItems) {
        //todo check it
        Set<String> inStockSkuCodes = inventoryResponses.stream()
                .filter(InventoryResponse::isInStock)
                .map(InventoryResponse::getSkuCode)
                .collect(Collectors.toSet());
        return inStockSkuCodes.containsAll(skuCodesOfOrderLineItems);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        //todo fixed
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    private Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = getOrderLineItemsFromOrderRequest(orderRequest);
        order.setOrderLineItemsList(orderLineItems);
        return order;
    }

    private List<OrderLineItems> getOrderLineItemsFromOrderRequest(OrderRequest orderRequest) {
        return orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
