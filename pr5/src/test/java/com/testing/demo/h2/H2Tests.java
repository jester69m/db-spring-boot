package com.testing.demo.h2;

import com.testing.demo.employee.Employee;
import com.testing.demo.employee.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

@DataJpaTest(properties = {"spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:db",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa"})
public class H2Tests {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void findTest(){
        String name = "Name1";
        Optional<Employee> e = repository.findByNameIgnoreCase(name);
        Assertions.assertTrue(e.isPresent());
        name = "name1";
        e = repository.findByNameIgnoreCase(name);
        Assertions.assertTrue(e.isPresent());
    }

    @Test
    public void updateTest(){
        String name = "name1";
        Optional<Employee> e = repository.findByNameIgnoreCase(name);
        Assertions.assertTrue(e.isPresent());
        Employee e2 = e.get();
        name = "changedName1";
        e2.setName(name);
        repository.save(e2);
        e = repository.findByNameIgnoreCase(name);
        Assertions.assertTrue(e.isPresent());
        Assertions.assertEquals("manager", e.get().getJob());

    }

}
