package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.OrderRequest;
import com.jennifer.entity.Order;
import com.jennifer.entity.OrderItem;
import com.jennifer.entity.Product;
import com.jennifer.entity.User;
import com.jennifer.exception.CustomException;
import com.jennifer.repository.OrderRepository;
import com.jennifer.repository.ProductRepository;
import com.jennifer.repository.UserRepository;
import com.jennifer.service.OrderService;
import com.jennifer.utils.LoggedInUserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LoggedInUserUtils utils;












}
