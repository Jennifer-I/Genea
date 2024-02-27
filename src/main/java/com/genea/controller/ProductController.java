package com.genea.controller;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ProductSearchResponse;
import com.genea.entity.Product;
import com.genea.enums.ProductCategory;
import com.genea.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getManufacturerByName/{manufacturersName}")
    public String getManufacturerByName(@PathVariable String manufacturersName) {
        return productService.getManufacturerByName(manufacturersName).toString();
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/getProductsByCategory/{category}")
    public ResponseEntity<String> getProductsByCategory(@PathVariable ProductCategory category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category).toString());
    }
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/getProductByManufacturersName/{manufacturersName}")
    public ResponseEntity<List<Product>> getProductsByManufacturersName(@PathVariable String manufacturersName) {
        return ResponseEntity.ok(productService.getProductByManufacturersName(manufacturersName));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/searchProductByKeyword/{keyword}")
    public ResponseEntity<List<ProductSearchResponse>> searchProductByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.getProductsByKeyword(keyword));
    }
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/getProductByManufacturerLocation/{location}")
    public ResponseEntity<List<ProductSearchResponse>> getProductByManufacturerLocation(@PathVariable String location) {
        return ResponseEntity.ok(productService.getProductByManufacturerLocation(location));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/searchProduct/{keyword}")
    public ResponseEntity<List<ProductSearchResponse>> searchProduct(@PathVariable String keyword,@RequestParam(required = false) Double price){
        List<ProductSearchResponse> searchResults = productService.searchProduct(keyword,price);
        return ResponseEntity.ok(searchResults);
    }




}
