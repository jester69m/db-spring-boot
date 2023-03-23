package com.jdbc.template.demo.employee.entity;


import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employees")
public class Employee implements Serializable{

    private Long id;
    private String firstName;
    private String secondName;
    private String jobTitle;
    @PositiveOrZero
    private int salary;
    private String phoneNumber;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee)o;
        return Objects.equals(id,that.id);
    }

    @Override
    public int hashCode(){ return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
