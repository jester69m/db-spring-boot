package com.example.transactional.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max=20)
    private String name;
    @NotNull
    private String jobTitle;
    @PositiveOrZero
    private int salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary && Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(jobTitle, employee.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
