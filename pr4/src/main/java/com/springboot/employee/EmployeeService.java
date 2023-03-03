package com.springboot.employee;

import java.util.Optional;

public interface EmployeeService {

    public Optional<Employee> findByFirstName(String firstName);
}
