package com.genea.controller;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.enums.ProductCategory;
import com.genea.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public String addProduct(@RequestBody CreateProductRequest productRequest) {
        return productService.addProduct(productRequest);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createManufacturer")
    public String createManufacturer(@RequestBody ManufacturerRequest manufacturerRequest) {
        return productService.createManufacturer(manufacturerRequest);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllProducts")
    public String getAllProducts() {
        return productService.getAllProducts().toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllManufacturers")
    public String getAllManufacturers() {
        return productService.getAllManufacturers().toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getProductById/{id}")
    public String getProductById(@PathVariable Long id) {

        return productService.getProductById(id).toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getManufacturerById/{id}")
    public String getManufacturerById(@PathVariable Long id) {
        return productService.getManufacturerById(id).toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/getProductByName/{name}")
    public String getProductByName(@PathVariable String name) {
        return productService.getProductByName(name).toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getManufacturerByName/{name}")
    public String getManufacturerByName(@PathVariable String name) {
        return productService.getManufacturerByName(name).toString();
    }

    @PreAuthorize("hasRole('ADMIN','CUSTOMER')")
    @GetMapping("/getProductsByCategory/{category}")
    public String getProductsByCategory(@PathVariable ProductCategory category) {
        return productService.getProductsByCategory(category).toString();
    }



}
