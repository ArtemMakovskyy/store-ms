package com.store.streamsql.service;

import com.store.streamsql.model.izolations.InventoryIzolation;
import com.store.streamsql.repository.isolations.InventoryIzolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
@RequiredArgsConstructor
public class IzolationDemoService {

    private final InventoryIzolationRepository inventoryRepo;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public int readQuantity(Long productId) {
        InventoryIzolation inventory = inventoryRepo.findByProductId(productId);
        System.out.println("[T2] Чтение количества: " + inventory.getQuantity());
        return inventory.getQuantity();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void updateQuantity(Long productId, int newQuantity) {
        InventoryIzolation inventory = inventoryRepo.findByProductId(productId);
        inventory.setQuantity(newQuantity);
        System.out.println("[T1] Обновление количества на: " + newQuantity);
        sleep(5000); // имитируем медленный коммит
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
