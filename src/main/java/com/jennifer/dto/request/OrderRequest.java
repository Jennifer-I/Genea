package com.jennifer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jennifer.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class OrderRequest {
private String quantity;
private Double price;
private Double totalCost;
private String paymentType;
private String DeliveryMethod;
private String shippingAddress;
private List<Long> cartItemIds;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime dateOrdered;
private String orderStatus;
private Double costOfProducts;


}
