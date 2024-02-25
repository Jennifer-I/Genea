package com.genea.repository;

import com.genea.entity.Product;
import com.genea.enums.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ProductRepository extends JpaRepository<Product,Long> {


    List<Product> findByCategory(ProductCategory category);

    List<Product> findProductsByManufacturerManufacturersName(String manufacturersName);

    List<Product> findProductByManufacturerLocation(String location);
//    @Query("SELECT product FROM Product product WHERE LOWER(product.productName) LIKE %LOWER(:keyword)% OR LOWER(product.description) LIKE %LOWER(:keyword)%")

    @Query("SELECT product FROM Product product WHERE LOWER(product.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(product.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")

//    @Query("SELECT product FROM Product product WHERE product.productName LIKE %:keyword% OR product.description LIKE %:keyword%")
    List<Product> searchProductByKeyword(@Param("keyword") String keyword);

    Optional<Product> findByProductName(String productName);
}