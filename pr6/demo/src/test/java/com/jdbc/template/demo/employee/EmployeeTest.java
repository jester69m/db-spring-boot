package com.jdbc.template.demo.employee;

import com.jdbc.template.demo.employee.entity.Employee;
import com.jdbc.template.demo.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class EmployeeTest {
//public class EmployeeTest extends BaseTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    public void createEmployeeTest() {
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest");
        emp.setSecondName("secondNameTest");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");

        emp = repository.create(emp);
        Assertions.assertNotNull(emp.getId());
    }

    @Test
    public void getByIdTest() {
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest");
        emp.setSecondName("secondNameTest");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");

        emp = repository.create(emp);
        Optional<Employee> empFromDB = repository.findById(emp.getId());
        Assertions.assertTrue(empFromDB.isPresent());
    }

    @Test
    public void findAllTest() {
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest1");
        emp.setSecondName("secondNameTest1");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(10000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        List<Employee> employees = repository.findAll();
        Assertions.assertEquals(2,employees.size());
    }

    @Test
    public void deleteByIdTest(){
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest1");
        emp.setSecondName("secondNameTest1");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(10000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        List<Employee> employees = repository.findAll();
        Assertions.assertEquals(2,employees.size());
        repository.deleteById(emp2.getId());
        employees = repository.findAll();
        Assertions.assertEquals(1, employees.size());
    }

    @Test
    public void deleteCollectionOfEmployeesTest(){
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest1");
        emp.setSecondName("secondNameTest1");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(10000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        List<Employee> employees = repository.findAll();
        Assertions.assertEquals(2,employees.size());
        repository.delete(employees);
        employees = repository.findAll();
        Assertions.assertEquals(0, employees.size());
    }

    @Test
    public void deleteAllTest(){
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest1");
        emp.setSecondName("secondNameTest1");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(10000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        List<Employee> employees = repository.findAll();
        Assertions.assertEquals(2,employees.size());
        repository.deleteAll();
        employees = repository.findAll();
        Assertions.assertEquals(0, employees.size());
    }

    @Test
    public void updateTest() {
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest");
        emp.setSecondName("secondNameTest");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");

        emp = repository.create(emp);

        emp.setFirstName("changed");
        repository.update(emp);

        Optional<Employee> empFromDB = repository.findById(emp.getId());
        Assertions.assertTrue(empFromDB.isPresent());
        Assertions.assertEquals(emp.getFirstName(), empFromDB.get().getFirstName());
    }

    public void avgSalaryByJobTitleTest(){
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest");
        emp.setSecondName("secondNameTest");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp3 = new Employee();
        emp3.setFirstName("firstNameTest");
        emp3.setSecondName("secondNameTest");
        emp3.setJobTitle("AnotherJobTitle");
        emp3.setSalary(1000000);
        emp3.setPhoneNumber("+380*********");
        emp3 = repository.create(emp3);

        double avg = repository.getAvgSalaryByJobTitle(emp.getJobTitle());
        Assertions.assertEquals("10000", avg);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(20000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        avg = repository.getAvgSalaryByJobTitle(emp.getJobTitle());
        Assertions.assertEquals("15000", avg);
    }

    @Test
    public void batchUpdateTest(){
        Employee emp = new Employee();
        emp.setFirstName("firstNameTest1");
        emp.setSecondName("secondNameTest1");
        emp.setJobTitle("jobTitleTest");
        emp.setSalary(10000);
        emp.setPhoneNumber("+380*********");
        emp = repository.create(emp);

        Employee emp2 = new Employee();
        emp2.setFirstName("firstNameTest2");
        emp2.setSecondName("secondNameTest2");
        emp2.setJobTitle("jobTitleTest");
        emp2.setSalary(20000);
        emp2.setPhoneNumber("+380*********");
        emp2 = repository.create(emp2);

        emp.setFirstName("changedFirstName1");
        emp2.setFirstName("changedFirstName2");

        Map<Long, Employee> employeeById = new HashMap<>();
        employeeById.put(emp.getId(),emp);
        employeeById.put(emp2.getId(),emp2);

        List<Employee> forUpdate = new ArrayList<>();
        forUpdate.add(emp);
        forUpdate.add(emp2);

        repository.batchUpdate(forUpdate);

        List<Employee> empInDB = repository.findAll();

        Assertions.assertEquals(2, empInDB.size());
        Assertions.assertEquals(employeeById.get(empInDB.get(0).getId()).getFirstName(), empInDB.get(0).getFirstName());
        Assertions.assertEquals(employeeById.get(empInDB.get(1).getId()).getFirstName(), empInDB.get(1).getFirstName());



    }





}
