package com.genea.service;

import com.genea.dto.request.CreateProductRequest;
import com.genea.dto.request.ManufacturerRequest;
import com.genea.dto.response.ApiResponse;

public interface ProductService {
    String addProduct(CreateProductRequest productRequest);


    String createManufacturer(ManufacturerRequest manufacturerRequest);
}
