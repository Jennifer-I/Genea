package com.jennifer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private String productName;
    private int quantity;
    private int totalQuantity;
    private Double price;
    private Double subTotal;
}
