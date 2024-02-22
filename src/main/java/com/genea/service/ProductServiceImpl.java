package com.genea.service;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ApiResponse;
import com.genea.entity.Manufacturer;
import com.genea.entity.Product;
import com.genea.entity.User;
import com.genea.enums.Role;
import com.genea.exception.CustomException;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.ManufacturerRepository;
import com.genea.repository.ProductRepository;
import com.genea.repository.UserRepository;
import com.genea.utils.LoggedInUserUtils;
import jakarta.persistence.Id;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final LoggedInUserUtils utils;
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImpl(UserRepository userRepository, LoggedInUserUtils utils, ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public String addProduct(CreateProductRequest productRequest) {
        String loggedInUser = utils.getLoginUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null && user.getRole() == Role.ADMIN) {
            Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerId())
                    .orElseThrow(() -> new CustomException("Manufacturer not found"));
            Product product = Product.builder()
                    .price(productRequest.getPrice())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .image(productRequest.getImage())
                    .category(productRequest.getCategory())
                    .manufacturer(manufacturer)
                    .stock(productRequest.getStock())
                    .build();
            productRepository.save(product);

            return "product added successfully";

        } else {
            return "failed to add product";
        }
    }


    @Override
    public String createManufacturer(ManufacturerRequest manufacturerRequest) {
        String loggedInUser = utils.getLoginUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null && user.getRole() == Role.ADMIN) {
            Manufacturer manufacturer = Manufacturer.builder()
                    .name(manufacturerRequest.getName())
                    .location(manufacturerRequest.getLocation())
                    .build();
            manufacturerRepository.save(manufacturer);
            return "manufacturer added successfully";
        }

        return "failed to add manufacturer";
    }
}