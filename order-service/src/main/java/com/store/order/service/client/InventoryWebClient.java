package com.store.order.service.client;

import com.store.order.service.dto.InventoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component("inventoryWebClient")
@RequiredArgsConstructor
public class InventoryWebClient {
    private final WebClient.Builder webClientBuilder;
    public boolean isInStock(String skuCode, Integer quantity) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("INVENTORY-SERVICE")
                        .path("/api/inventory")
                        .queryParam("skuCode", skuCode)
                        .queryParam("quantity", quantity)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

}

//    public  List<InventoryResponse> inventoriesResponseBySkuCodes(List<String>skuCodes){
//      return webClientBuilder.build()
//              .get()
//              .uri(uriBuilder -> uriBuilder
//                      .scheme("http")
//                      .host("INVENTORY-SERVICE")
//                      .path("/api/inventory")
//                      .queryParam("skuCode", skuCodes)
//                      .build()
//              )
//              .retrieve()
//              .bodyToFlux(InventoryResponse.class)
//              .collectList()
//              .block();
//  }