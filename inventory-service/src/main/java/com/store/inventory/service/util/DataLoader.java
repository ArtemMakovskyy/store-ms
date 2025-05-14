package com.store.inventory.service.util;

import com.store.inventory.service.model.Inventory;
import com.store.inventory.service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        Inventory classic = new Inventory();
        classic.setSkuCode("classic_guitar");
        classic.setQuantity(100);

        Inventory instrumental = new Inventory();
        instrumental.setSkuCode("instrumental_guitar");
        instrumental.setQuantity(0);

        inventoryRepository.save(classic);
        inventoryRepository.save(instrumental);
    }
}
