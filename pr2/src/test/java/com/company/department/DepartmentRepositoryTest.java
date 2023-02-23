package com.company.department;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository repository;

    @AfterEach
    void clearData() {
        repository.deleteAll();
    }

    @Test
    void findById_shouldReturnDepartment() {
        Department savedDepartment = repository.save(new Department(null, "department name"));

        Optional<Department> foundDepartment = repository.findById(savedDepartment.getId());
        assertTrue(foundDepartment.isPresent());
        assertEquals(savedDepartment, foundDepartment.get());
    }

    @Test
    void findAll_shouldReturnDepartments() {
        List<Department> savedDepartments = repository.saveAll(List.of(
                new Department(null, "Department 1"),
                new Department(null, "Department 2"),
                new Department(null, "Department 3"),
                new Department(null, "Department 4")
        ));

        List<Department> foundDepartments = repository.findAll();
        assertEquals(savedDepartments.size(), foundDepartments.size());
    }

    @Test
    void saveDepartment_shouldReturnValidDepartment() {
        Department departmentToSave = new Department(null, "department name");

        Department savedDepartment = repository.save(departmentToSave);

        assertNotNull(savedDepartment.getId());
        assertEquals(departmentToSave.getName(), savedDepartment.getName());
    }

    @Test
    void deleteDepartment_shouldThrowException() {
        Department departmentToDelete = repository.save(new Department(null, "department name"));
        assertTrue(repository.existsById(departmentToDelete.getId()));

        repository.deleteById(departmentToDelete.getId());
        assertFalse(repository.existsById(departmentToDelete.getId()));
    }
}
