package com.testing.demo.testcontainer;

import com.testing.demo.employee.Employee;
import com.testing.demo.employee.EmployeeRepository;
import com.testing.demo.testcontainer.util.ContainerBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql("/data.sql")
public class EmployeeTest extends ContainerBaseTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void findAllTest(){
        List<Employee> emp = repository.findAll();
        Assertions.assertEquals(9,emp.size());
    }

    @Test
    void findAllByJobTitleTest(){
        String job_title = "security";
        List<Employee> found = repository.findAllByJob(job_title);
        Assertions.assertFalse(found.isEmpty());
        for(Employee e: found)
            Assertions.assertEquals(job_title, e.getJob());
    }

    @Test
    void findAllBySalaryBetween(){
        List<Employee> found = repository.findAllBySalaryBetween(20000, 22000);
        Assertions.assertFalse(found.isEmpty());
        for(Employee e : found){
            Assertions.assertTrue(e.getSalary() >= 20000);
            Assertions.assertTrue(e.getSalary() <= 22000);
        }
    }
}
