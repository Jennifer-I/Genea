package com.genea.entity;

import com.genea.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;


    private Integer stock;
    private ProductCategory category;
    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;




}
