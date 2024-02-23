package com.genea.service;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.entity.Manufacturer;
import com.genea.entity.Product;
import com.genea.entity.User;
import com.genea.enums.ProductCategory;
import com.genea.exception.CustomException;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.ManufacturerRepository;
import com.genea.repository.ProductRepository;
import com.genea.repository.UserRepository;
import com.genea.utils.LoggedInUserUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
        String loggedInUser = utils.getLoggedInUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
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
        }
        return "failed to add product";

    }


    @Override
    public String createManufacturer(ManufacturerRequest manufacturerRequest) {
        String loggedInUser = utils.getLoggedInUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            Manufacturer manufacturer = Manufacturer.builder()
                    .name(manufacturerRequest.getName())
                    .location(manufacturerRequest.getLocation())
                    .build();
            manufacturerRepository.save(manufacturer);
            return "manufacturer added successfully";
        }

        return "failed to add manufacturer";
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("An error occurred while fetching products");
        }
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        String loggedInUser = utils.getLoggedInUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            return productRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Product not found"));
        }
        return null;
    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        String loggedInUser = utils.getLoggedInUser();
        User user = userRepository.findByEmail(loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user != null) {
            return manufacturerRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Manufacturer not found"));
        }
        return null;
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "product deleted successfully";
    }

    @Override

    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new CustomException("Product not found"));
    }

    @Override
    public Manufacturer getManufacturerByName(String name) {
        return (Manufacturer) manufacturerRepository.findByName(name)
                .orElseThrow(() -> new CustomException("Manufacturer not found"));
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }




}