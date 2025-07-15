package com.store.streamsql.model.izolations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "izolation_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIzolation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime placedAt;

    @ManyToOne
    private CustomerIzolation customer;

    @ManyToMany
    private List<ProductIzolation> products = new ArrayList<>();
}
