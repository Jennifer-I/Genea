package com.jennifer.dto.response;

import com.jennifer.entity.OrderItem;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private String orderNumber;
    private String orderStatus;
    private List<OrderItem> orderItems;
    private Double DeliveryFee;
    private Double costOfProducts;
    private Double totalCost;
    private String paymentType;
    private String DeliveryMethod;
    private String shippingAddress;
    private LocalDateTime dateOrdered;
    private LocalDateTime dateDelivered;

}
