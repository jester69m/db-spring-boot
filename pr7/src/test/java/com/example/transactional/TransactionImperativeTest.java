package com.example.transactional;

import com.example.transactional.employee.Employee;
import com.example.transactional.employee.EmployeeService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.SneakyThrows;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class TransactionImperativeTest {

    @Autowired
    private EmployeeService service;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @BeforeEach
    void setUp() {
        service.deleteAll();
    }

    @Test
    void addWithReadUncommittedWithDirtyRead() throws InterruptedException {
        executor.execute(() -> {
            try {
                Employee emp = new Employee();
                emp.setName("first");
                emp.setJobTitle("title1");
                emp.setSalary(10000);

                service.createWithTimeout(emp);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executor.execute(() -> {
            try {

                Employee emp = new Employee();
                emp.setName("first");
                emp.setJobTitle("title1");
                emp.setSalary(10000);

                service.createWithTimeout(emp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);

        List<Employee> employees = service.getAllWithReadUncommitted();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    void addWithReadCommittedWithoutDirtyRead() {
        Runnable save1 = () -> {
            try {
                Employee emp = new Employee();
                emp.setName("first");
                emp.setJobTitle("title1");
                emp.setSalary(10000);

                service.createWithTimeout(emp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable save2 = () -> {
            try {
                Employee emp = new Employee();
                emp.setName("second");
                emp.setJobTitle("title2");
                emp.setSalary(10000);

                service.createWithTimeout(emp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        executor.execute(save1);
        executor.execute(save2);

        List<Employee> employees = service.getAllWithReadCommitted();
        assertThat(employees.size()).isEqualTo(0);
    }

    @Test
    void getAllWithReadCommittedAndDataUpdatingShouldBeNonRepeatableRead() throws InterruptedException {
        Employee emp = new Employee();
        emp.setName("first");
        emp.setJobTitle("title1");
        emp.setSalary(10000);
        Employee savedEmployee = service.create(emp);

        Runnable update = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Employee emp1 = new Employee();
            emp1.setName("second");
            emp1.setJobTitle("title1");
            emp1.setSalary(10000);
            service.update(emp.getId(), emp1);
        };

        Runnable runnable = new Runnable() {
            @Override
            @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
            public void run() {
                List<Employee> allWithReadCommitted = service.getAll();
                assertThat(allWithReadCommitted.size()).isEqualTo(1);
                assertThat(allWithReadCommitted.get(0).getName()).isEqualTo(emp.getName());

                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                allWithReadCommitted = service.getAll();
                assertThat(allWithReadCommitted.size()).isEqualTo(1);
                assertThat(allWithReadCommitted.get(0).getName()).isNotEqualTo(emp.getName());
            }
        };

        executor.execute(runnable);
        executor.execute(update);
        executor.awaitTermination(8000, TimeUnit.MILLISECONDS);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    void getAllWithRepeatableAndDataUpdatingShouldNotBeNotRepeatableRead() throws InterruptedException {
        Employee emp = new Employee();
        emp.setName("first");
        emp.setJobTitle("title1");
        emp.setSalary(10000);

        Employee savedEmployee = service.create(emp);

        List<Employee> allWithReadCommitted = service.getAllWithRepeatableRead();
        assertThat(allWithReadCommitted.size()).isEqualTo(1);
        assertThat(allWithReadCommitted.get(0).getName()).isEqualTo(emp.getName());

        Employee emp2 = new Employee();
        emp2.setName("first");
        emp2.setJobTitle("title1");
        emp2.setSalary(10000);
        executor.execute(() -> service.update(savedEmployee.getId(), emp2));
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);

        allWithReadCommitted = service.getAllWithRepeatableRead();
        assertThat(allWithReadCommitted.size()).isEqualTo(1);
        assertThat(allWithReadCommitted.get(0).getName()).isEqualTo(emp.getName());
    }

    @Test
    @Transactional
    void testCountWithDifferentIsolationTypes() throws InterruptedException {
        Employee emp = new Employee();
        emp.setName("first");
        emp.setJobTitle("title1");
        emp.setSalary(10000);
        service.create(emp);
        executor.execute(() -> {
            try {
                service.addWithException(new Employee());
            } catch (Exception ignored) {

            }
        });
        executor.execute(() -> {
            try {
                Employee emp2 = new Employee();
                emp2.setName("second");
                emp2.setJobTitle("title1");
                emp2.setSalary(10000);
                service.createWithTimeout(emp2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executor.awaitTermination(1500, TimeUnit.MILLISECONDS);
        executor.execute(() -> {
            assertThat(service.countWithReadUncommitted()).isEqualTo(2L);
        });
        executor.execute(() -> {
            assertThat(service.countWithReadCommitted()).isEqualTo(1L);
        });
        executor.execute(() -> {
            assertThat(service.countWithRepeatableRead()).isEqualTo(2L);
        });
    }
}
