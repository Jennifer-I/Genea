package com.jennifer.dto.response;


import com.jennifer.dto.request.CartItemDto;
import com.jennifer.entity.CartItem;
import com.jennifer.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private double subTotal;
    private int totalQuantity;
    private List<CartItemDto> cartItem;



}
