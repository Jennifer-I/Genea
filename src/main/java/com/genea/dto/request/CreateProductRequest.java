package com.genea.dto.request;

import com.genea.entity.Manufacturer;
import com.genea.entity.Review;
import com.genea.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private ProductCategory category;

    private String imageUrl;


    private Long manufacturerId;



}
