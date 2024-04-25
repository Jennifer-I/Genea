package com.jennifer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;





}
