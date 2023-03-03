package com.springboot;

import com.springboot.employee.Employee;
import com.springboot.employee.EmployeeRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ValidationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void checkUkrainePhoneNumberAnnotation(){
        Employee e1 = new Employee("Abdul","Magomedov","manager",25000,"+380953413409","Ukraine, Kyiv");
        Employee e2 = new Employee("NeAbdul","Magaretov","manager",30000,"+7953543209","Ukraine, Kyiv");

        Assertions.assertDoesNotThrow(() ->employeeRepository.saveAndFlush(e1));
        Assertions.assertThrows(ConstraintViolationException.class,() ->employeeRepository.saveAndFlush(e2));
        e2.setPhoneNumber("+380971666666");
        Assertions.assertDoesNotThrow(() ->employeeRepository.saveAndFlush(e2));
    }
}
