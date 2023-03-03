package com.springboot.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository repos;

    @PersistenceContext
    EntityManager em;

    public EmployeeServiceImpl(EmployeeRepository repos) {
        this.repos = repos;
    }
    @Override
    public Optional<Employee> findByFirstName(String firstName){
        return repos.findByFirstName(firstName);
    }
}
