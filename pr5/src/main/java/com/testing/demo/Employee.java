package com.testing.demo;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length = 50)
    private String secondName;
    @Column(nullable = false,length = 50)
    private String jobTitle;
    @PositiveOrZero
    @Column(nullable = false)
    private int salary;
    @Column(length = 13,nullable = false)
    private String phoneNumber;
    @Column(length = 100)
    private String address;

    public Employee(String firstName, String secondName, String jobTitle, int salary, String phoneNumber, String address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
