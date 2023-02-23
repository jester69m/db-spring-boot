package com.company.employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @AfterEach
    void clearData() {
        repository.deleteAll();
    }

    @Test
    void findById_shouldReturnEmployee() {
        Employee savedEmployee = repository.save(new Employee(null, "name1","name2","male",null,null));

        Optional<Employee> foundEmployee = repository.findById(savedEmployee.getId());
        assertTrue(foundEmployee.isPresent());
        assertEquals(savedEmployee, foundEmployee.get());
    }
    @Test
    void findAll_shouldReturnEmployees() {
        List<Employee> savedEmployees = repository.saveAll(List.of(
                new Employee(null, "name11","name21","male",null,null),
                new Employee(null, "name12","name22","female",null,null),
                new Employee(null, "name13","name23","male",null,null),
                new Employee(null, "name14","name24","male",null,null)
        ));

        List<Employee> foundEmployees = repository.findAll();
        assertEquals(savedEmployees.size(), foundEmployees.size());
    }

    @Test
    void saveEmployee_shouldReturnValidEmployee() {
        Employee employeeToSave = new Employee(null, "name1","name2","male",null,null);

        Employee savedEmployee = repository.save(employeeToSave);

        assertNotNull(savedEmployee.getId());
        assertEquals(employeeToSave.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeeToSave.getSecondName(), savedEmployee.getSecondName());
        assertEquals(employeeToSave.getGender(), savedEmployee.getGender());
    }

    @Test
    void deleteEmployee_shouldThrowException() {
        Employee employeeToDelete = repository.save(new Employee(null, "name1","name2","male",null,null));
        assertTrue(repository.existsById(employeeToDelete.getId()));

        repository.deleteById(employeeToDelete.getId());
        assertFalse(repository.existsById(employeeToDelete.getId()));
    }
}
