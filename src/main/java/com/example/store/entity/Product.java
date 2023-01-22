package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Column(unique = true)
    private String name;

    private Double price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;
}
