package com.genea.service.serviceImpl;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ProductSearchResponse;
import com.genea.entity.Manufacturer;
import com.genea.entity.Product;
import com.genea.entity.User;
import com.genea.enums.ProductCategory;
import com.genea.exception.CustomException;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.ManufacturerRepository;
import com.genea.repository.ProductRepository;
import com.genea.repository.UserRepository;
import com.genea.service.ProductService;
import com.genea.utils.LoggedInUserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    .productName(productRequest.getName())
                    .description(productRequest.getDescription())
                    .imageUrl(productRequest.getImageUrl())
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
                    .manufacturersName(manufacturerRequest.getManufacturerName())
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

        return productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));

    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Manufacturer not found"));

    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "product deleted successfully";
    }

    @Override
    public Product getProductByName(String productName) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (product.isEmpty()) {
            throw new CustomException("Product not found");
        }
        return product.get();


    }

    @Override
    public Manufacturer getManufacturerByName(String manufacturersName) {
        return (Manufacturer) manufacturerRepository.findByManufacturersName(manufacturersName)
                .orElseThrow(() -> new CustomException("Manufacturer not found"));
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new CustomException("Product not found");
        }
        return products;
    }

    @Override
    public List<Product> getProductByManufacturersName(String manufacturersName) {
        List<Product> products = productRepository.findProductsByManufacturerManufacturersName(manufacturersName);
        if (products.isEmpty()) {
            throw new CustomException("Product not found");
        }
        return products;
    }

    @Override
    public List<ProductSearchResponse> getProductByManufacturerLocation(String location) {
        List<Product> products = productRepository.findProductByManufacturerLocation(location);
        return products.stream()
                .map(ProductSearchResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductSearchResponse> getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.searchProductByKeyword(keyword);
        return products.stream()
                .map(ProductSearchResponse::new)
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductSearchResponse> searchProduct(String keyword, Double price) {
        List<Product> products = productRepository.searchProduct(keyword, price);
        return products.stream()
                .map(ProductSearchResponse::new)
                .collect(Collectors.toList());
    }


}
























//public List<Product> searchProductByKeyword(String keyword) {
//    List<Product> products = productRepository.searchProductByKeyword(keyword);
//    List<Product> filteredProducts = new ArrayList<>();
//
//    for (Product product : products) {
//        if (product.getProductName().toLowerCase().contains(keyword.toLowerCase()) ||
//                product.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
//            filteredProducts.add(product);
//        }
//    }
//
//    if (filteredProducts.isEmpty()) {
//        throw new CustomException("Product not found");
//    }
//    return filteredProducts;
//}


