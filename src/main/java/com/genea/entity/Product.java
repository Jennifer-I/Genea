package com.genea.entity;

import com.genea.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String description;

    private Double price;

    private Integer quantity;

    @OneToOne(mappedBy = "product")
    private Inventory inventory;


    private Integer stock;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;


    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Override
    public String toString() {
        return String.format("Product {\n" +
                        "\tid=%d,\n" +
                        "\tproductName='%s',\n" +
                        "\tdescription='%s',\n" +
                        "\tprice=%.2f,\n" +
                        "\tstock=%d,\n" +
                        "\tcategory=%s,\n" +
                        "\timage='%s',\n" +
                        "\treviews=%s,\n" +
                        "\tmanufacturer=" + (manufacturer != null ? manufacturer.getManufacturersName() : "null") + "\n"+
                        "}\n",
                id, productName, description, price, stock, category, imageUrl, reviews);
    }


}
