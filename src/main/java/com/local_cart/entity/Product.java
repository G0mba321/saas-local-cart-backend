package com.local_cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer inStock = 0;

    private Double price;

    @Column(length = 1000)
    private String description;

    @JdbcTypeCode((SqlTypes.JSON))
    private Map<String, String> extraDetails = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_country_id")
    private Country originCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @PrePersist
    @PreUpdate
    private void cleanEmptyDetails() {
        if (extraDetails != null) {
            extraDetails.values().removeIf(value -> value == null || value.trim().isEmpty());
        }
    }

}