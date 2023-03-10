package com.testing.demo.h2;

import com.testing.demo.Employee;
import com.testing.demo.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Sql("pr5/src/main/resources/data.sql")
public class H2Tests {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void saveTest(){
        Employee e = new Employee("name1","name2","sales",10000,"+380957365475","Ukraine,Lviv");
        repository.save(e);
        Optional<Employee> e2 = repository.findByFirstName(e.getFirstName());
        Assert.assertEquals(e,e2.get().getFirstName());
    }

    @Test
    public void updateTest(){
        Employee e = new Employee("name1","name2","sales",10000,"+380957365475","Ukraine,Lviv");
        repository.save(e);
        Optional<Employee> e2 = repository.findByFirstName(e.getFirstName());
        Assert.assertEquals("sales",e2.get().getJobTitle());
        e.setJobTitle("director");
        e.setSalary(35000);
        repository.save(e);
        e2 = repository.findByFirstName(e.getFirstName());
        Assert.assertEquals("director",e2.get().getJobTitle());
        Assert.assertEquals(35000,e2.get().getSalary());
    }

}
