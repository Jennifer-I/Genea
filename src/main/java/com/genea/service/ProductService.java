package com.genea.service;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ApiResponse;
import com.genea.entity.Manufacturer;
import com.genea.entity.Product;
import com.genea.enums.ProductCategory;

import java.util.List;

public interface ProductService {
    String addProduct(CreateProductRequest productRequest);


    String createManufacturer(ManufacturerRequest manufacturerRequest);

    List<Product> getAllProducts();

    List<Manufacturer> getAllManufacturers();

    Product getProductById(Long id);

    Manufacturer getManufacturerById(Long id);

    String deleteProduct(Long id);

    Product getProductByName(String name);

    Manufacturer getManufacturerByName(String name);

    List<Product> getProductsByCategory(ProductCategory category);
}
