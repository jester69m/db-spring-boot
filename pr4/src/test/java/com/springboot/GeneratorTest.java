package com.springboot;

import com.springboot.employee.Employee;
import com.springboot.employee.EmployeeRepository;
import com.springboot.employee.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class GeneratorTest {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeeServiceImpl service;

    @Test
    void testIdCreating(){
        Employee e = new Employee("Abdul","Magomedov","manager",25000,"+380953413409","Ukraine, Kyiv");
        Employee e1 = new Employee("Abdul1","Magomedov","manager",25000,"+380953413409","Ukraine, Kyiv");
        Assertions.assertDoesNotThrow(() -> repository.saveAndFlush(e));
        Assertions.assertDoesNotThrow(() -> repository.saveAndFlush(e1));
        Optional<Employee> tryToFind = service.findByFirstName(e1.getFirstName());
        Assert.assertEquals(tryToFind.get().getId(),2);
    }
}
