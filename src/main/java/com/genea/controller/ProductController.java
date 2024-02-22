package com.genea.controller;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ApiResponse;
import com.genea.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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



}
