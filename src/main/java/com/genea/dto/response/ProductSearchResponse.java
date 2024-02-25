package com.genea.dto.response;

import com.genea.entity.Product;
import com.genea.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchResponse {
    private Long id;

    private String productName;

    private String description;

    private Double price;
    private ProductCategory category;
    private byte[] image;
    private String manufacturersName;

    private String location;


    public ProductSearchResponse(Product  product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.image = product.getImage();
        this.manufacturersName = product.getManufacturer().getManufacturersName();
        this.location = product.getManufacturer().getLocation();
    }
}
