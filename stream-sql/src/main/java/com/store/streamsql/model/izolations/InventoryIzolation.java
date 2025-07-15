package com.store.streamsql.model.izolations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "izolation_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryIzolation {
    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    @OneToOne
    private ProductIzolation product;
}
