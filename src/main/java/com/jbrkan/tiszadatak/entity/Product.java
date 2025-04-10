package com.jbrkan.tiszadatak.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


@Entity
@Table(
        name = "product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"}),
                @UniqueConstraint(columnNames = {"name"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 15, unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "price_eur", precision = 19, scale = 2)
    private BigDecimal priceEur;

    @Column(name = "price_usd", precision = 19, scale = 2)
    private BigDecimal priceUsd;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }

    @PrePersist
    @PreUpdate
    private void roundPrices() {
        this.priceEur = scale(this.priceEur);
        this.priceUsd = scale(this.priceUsd);
    }

    private BigDecimal scale(BigDecimal value) {
        return value == null ? null : value.setScale(2, RoundingMode.HALF_UP);
    }
}
