package com.jennifer.controller;

import com.jennifer.dto.response.CartResponseDto;
import com.jennifer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addProductToCart/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(productId));
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/removeProductFromCart/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/viewCart")
    public ResponseEntity<CartResponseDto> viewCart() {
        return ResponseEntity.ok(cartService.viewCart());
    }



}
