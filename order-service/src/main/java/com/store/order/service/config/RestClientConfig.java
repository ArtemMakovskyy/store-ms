package com.store.order.service.config;

import com.store.order.service.client.InventoryFeignClient;
import com.store.order.service.client.InventoryRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {
    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryRestClient inventoryRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .build();
        RestClientAdapter restClientAdapter
                = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryRestClient.class);

    }
}
