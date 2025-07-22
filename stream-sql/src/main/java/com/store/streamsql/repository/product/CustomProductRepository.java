package com.store.streamsql.repository.product;

import com.store.streamsql.model.product.Product;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProductRepository {
    private final EntityManager entityManager;

//    @PostConstruct
    public void init() {
        System.out.println("<<< CustomProductRepository init");
        List<Product> findAllProductsWithTag = findAllProductsWithTagVar2();
        findAllProductsWithTag.forEach(System.out::println);
    }

    public List<Product> findAllProductsWithTagVar1() {
        String jpql = "SELECT p FROM Product p JOIN FETCH p.tags";
        return entityManager.createQuery(jpql, Product.class)
                .getResultList();
    }

    public List<Product> findAllProductsWithTagVar2() {
        EntityGraph<Product> graph = entityManager.createEntityGraph(Product.class);
        graph.addSubgraph("tags");
        String jpql = "SELECT p FROM Product p";
        return entityManager.createQuery(jpql, Product.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getResultList();
    }

}
