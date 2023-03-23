package com.jdbc.template.demo.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name="fn_sn_index", columnList = "firstName ASC, secondName ASC"),
        @Index(name="vip_index", columnList = "vipStatus DESC")
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    @Positive
    private int age;
    private int balance;
    private boolean vipStatus;


}
