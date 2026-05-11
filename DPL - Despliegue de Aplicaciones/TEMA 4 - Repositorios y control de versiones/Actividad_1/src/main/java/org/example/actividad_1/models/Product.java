package org.example.actividad_1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private double stock;
    private String category;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
