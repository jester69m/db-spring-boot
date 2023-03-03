package com.springboot.employee;

import com.springboot.validation.UkrainePhoneNumber;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employees")
public class Employee {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "emp-id-generator")
    @GenericGenerator(name = "emp-id-generator",
            strategy = "com.springboot.generator.MyIdGenerator")
    @Column(name="employee_id")
    private long id;

    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    @NotNull
    private String jobTitle;
    @PositiveOrZero
    private int salary;
    @UkrainePhoneNumber
    private String phoneNumber;
    @Nullable
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
