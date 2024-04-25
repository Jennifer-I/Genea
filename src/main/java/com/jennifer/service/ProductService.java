package com.jennifer.service;

import com.jennifer.dto.request.CreateProductRequest;
import com.jennifer.dto.request.ManufacturerRequest;
import com.jennifer.dto.response.CartResponseDto;
import com.jennifer.dto.response.ProductSearchResponse;
import com.jennifer.entity.Manufacturer;
import com.jennifer.entity.Product;
import com.jennifer.enums.ProductCategory;

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

    List<Product> getProductByManufacturersName(String name);





    List<ProductSearchResponse> getProductByManufacturerLocation(String location);

    List<ProductSearchResponse> getProductsByKeyword(String keyword);


    List<ProductSearchResponse> searchProduct(String keyword, Double price);



}
