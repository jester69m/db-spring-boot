package com.testing.demo.testcontainer;

import com.testing.demo.Employee;
import com.testing.demo.testcontainer.util.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeTest extends BaseTest {

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
        Assert.assertEquals(3,emp.size());
    }

    @Test
    void findByJobTitleTest(){
        Assert.assertEquals("security",repository.findByJobTitle("security").get().getJobTitle());
    }
}
