package com.genea.service.serviceImpl;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.CartDto;
import com.genea.dto.response.ProductSearchResponse;
import com.genea.entity.*;
import com.genea.enums.ProductCategory;
import com.genea.exception.CustomException;
import com.genea.exception.UserNotFoundException;
import com.genea.repository.*;
import com.genea.service.ProductService;
import com.genea.utils.LoggedInUserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final LoggedInUserUtils utils;
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CartRepository cartRepository;


    public ProductServiceImpl(UserRepository userRepository, LoggedInUserUtils utils, ProductRepository productRepository, ManufacturerRepository manufacturerRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.cartRepository = cartRepository;
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


    @Override
    @Transactional
    public String addProductToCart(Long productId) {
        String loggedInUsername = utils.getLoggedInUser();
        if (loggedInUsername == null) {
            throw new CustomException("User not logged in");
        }
        User loggedInUser = userRepository.findByEmail(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = loggedInUser.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(loggedInUser);
            cart.setCartItems(new ArrayList<>());
        }
        Product productToAdd = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found"));
        if (productToAdd.getQuantity() <= 0) {
            throw new CustomException("Product is out of stock");
        }
        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productToAdd)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartRepository.save(cart);
                return "Product quantity increased in cart";
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productToAdd);
        newCartItem.setQuantity(1);
        newCartItem.setPrice(productToAdd.getPrice());
        newCartItem.setCart(cart);
        cartItems.add(newCartItem);
        cart.setCartItems(cartItems);
        cartRepository.save(cart);

        return "Product added to cart";
    }

    @Override
    public String removeProductFromCart(Long productId){
        String loggedInUsername = utils.getLoggedInUser();
        if (loggedInUsername == null) {
            throw new CustomException("User not logged in");
        }
        User loggedInUser = userRepository.findByEmail(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = loggedInUser.getCart();
        if (cart == null) {
            throw new CustomException("Cart not found");
        }
        Product productToRemove = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found"));
        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(productToRemove)) {
                cartItems.remove(cartItem);
                cartRepository.save(cart);
                return "Product removed from cart";
            }
        }
        throw new CustomException("Product not found in cart");
    }






}






