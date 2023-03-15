package com.testing.demo.testcontainer;

import com.testing.demo.Employee;
import com.testing.demo.EmployeeRepository;
import com.testing.demo.testcontainer.util.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeTest extends BaseTest {

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    public void init(){
        repository.saveAllAndFlush(List.of(
                new Employee("firstName6","secondName6","sales",10000,"+380953347959","Ukraine, Kyiv"),
                new Employee("firstName7","secondName7","manager",22000,"+380953347959","Ukraine, Kyiv"),
                new Employee("firstName8","secondName8","security",12000,"+380953347959","Ukraine, Kyiv")
        ));
    }
    @AfterEach
    public void clear(){
        repository.deleteAll();
    }

    @Test
    void findAllTest(){
        List<Employee> emp = repository.findAll();
        //Assertions.assertEquals(3, emp.size());
        Assertions.assertEquals(8,emp.size());
    }

    @Test
    void findAllByJobTitleTest(){
        Assertions.assertEquals("security", repository.findAllByJobTitle("security").get(0).getJobTitle());
    }

    @Test
    void findAllBySalaryBetween(){
        List<Employee> findedEmpl = repository.findAllBySalaryBetween(20000,22000);
        Assertions.assertNotEquals(0, findedEmpl.size());
        Assertions.assertEquals(3, findedEmpl.size());
        Employee emp = findedEmpl.get(0);
        Assertions.assertEquals("manager", emp.getJobTitle());
    }
}
