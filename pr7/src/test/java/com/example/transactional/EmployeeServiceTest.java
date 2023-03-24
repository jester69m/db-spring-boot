package com.example.transactional;

import com.example.transactional.employee.Employee;
import com.example.transactional.employee.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;
    private final JpaTransactionManager transactionManager = new JpaTransactionManager();

    @Test
    public void createEmployeeTest() {
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");

        Employee created = service.create(emp);
        Assertions.assertNotNull(created);
    }

    @Test
    public void findByIdTest() {
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        Employee created = service.create(emp);

        Optional<Employee> finded = service.find(created.getId());

        Assertions.assertTrue(finded.isPresent());
        Assertions.assertEquals(created.getFirstName(), finded.get().getFirstName());
    }

    @Test
    public void findAllTest(){
        List<Employee> created = new ArrayList<>();

        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        created.add(service.create(emp));

        Employee emp2 = new Employee();
        emp2.setFirstName("name3");
        emp2.setSecondName("name4");
        emp2.setSalary(35000);
        emp2.setJobTitle("manager");
        created.add(service.create(emp2));

        List<Employee> finded = service.findAll();

        Assertions.assertFalse(finded.isEmpty());
        Assertions.assertEquals(created.size(), finded.size());
    }

    @Test
    public void updateTest(){
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        Employee created = service.create(emp);

        Assertions.assertEquals("name1", created.getFirstName());

        emp.setFirstName("changed first name");
        Employee changed = service.update(created.getId(), emp);

        Assertions.assertEquals("changed first name", changed.getFirstName());
    }

    @Test
    public void deleteTest(){
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        Employee created = service.create(emp);

        service.delete(created.getId());
        Assertions.assertFalse(service.find(created.getId()).isPresent());
    }

    @Test
    public void increasingSalaryTest() {
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        Employee created = service.create(emp);
        service.increaseSalary(created.getId(), 10000);
        Employee updated = service.find(created.getId()).get();
        Assertions.assertEquals(40000, updated.getSalary());
    }

    @Test
    public void readCommittedWithRequiredTest() {
        Employee emp = new Employee();
        emp.setFirstName("name1");
        emp.setSecondName("name2");
        emp.setSalary(30000);
        emp.setJobTitle("manager");
        Employee created = service.create(emp);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        Employee updated = transactionTemplate.execute(status -> {
            Employee e = service.find(emp.getId()).get();
            e.setFirstName("name1");
            return service.update(e.getId(), e);
        });

        transactionTemplate.execute(status -> {
            Employee e = service.find(emp.getId()).get();
            Assertions.assertEquals("Jane", e.getFirstName());
            return null;
        });
    }

    @Test
    public void serializableRequiresNewTest() {
        // Create two employees
        Employee employee1 = new Employee();
        employee1.setFirstName("name1");
        employee1.setSecondName("name2");
        employee1.setSalary(30000);
        employee1.setJobTitle("manager");
        Employee employee2 = new Employee();
        employee2.setFirstName("name3");
        employee2.setSecondName("name4");
        employee2.setSalary(35000);
        employee2.setJobTitle("manager2");
        service.create(employee1);
        service.create(employee2);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        Employee updatedEmployee1 = transactionTemplate.execute(status -> {
            Employee e = service.find(employee1.getId()).get();
            e.setFirstName("changed first name");
            return service.update(e.getId(), e);
        });
        Employee updatedEmployee2 = transactionTemplate.execute(status -> {
            Employee e = service.find(employee2.getId()).get();
            e.setFirstName("Janie");
            return service.update(e.getId(), e);
        });

        transactionTemplate.execute(status -> {
            Employee e1 = service.find(updatedEmployee1.getId()).get();
            Assertions.assertEquals("changed first name", e1.getFirstName());
            Employee e2 = service.find(updatedEmployee2.getId()).get();
            Assertions.assertEquals("Janie", e2.getFirstName());
            return null;
        });
    }

}
