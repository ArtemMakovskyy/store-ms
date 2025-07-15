package com.store.streamsql.model.izolations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "izolation_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductIzolation {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private BigDecimal price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private InventoryIzolation inventory;
}
