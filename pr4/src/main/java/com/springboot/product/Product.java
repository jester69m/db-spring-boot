package com.springboot.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "employee-generator")
//    @GenericGenerator(name = "employee-generator", strategy = "com/springboot/generator/MyGenerator.java")
    private long id;

    @NotNull
    private String name;

    public Product(String name) {
        this.name = name;
    }
}
