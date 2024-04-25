package com.jennifer.dto.request;

import com.jennifer.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
