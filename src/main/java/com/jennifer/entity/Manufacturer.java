package com.jennifer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String manufacturersName;
    private String location;

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + manufacturersName + '\'' +
                ", location='" + location + '\'' +
                ", products=" + products +
                '}';
    }

}
