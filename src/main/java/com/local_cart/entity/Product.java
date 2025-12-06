package com.local_cart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@EqualsAndHashCode
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

    private Integer inStock;

    private Double price;

    @Column(length = 1000)
    private String description;

    //must be JSONB type in postgres
    private Map<String, Object> details;


}
