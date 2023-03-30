package com.testing.demo.employee;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name="employees")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Size(max = 20)
    @NotBlank
    private String name;
    @Size(max = 50)
    @NotBlank
    private String job;
    @PositiveOrZero
    @NotNull
    private int salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && salary == employee.salary && Objects.equals(name, employee.name) && Objects.equals(job, employee.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
