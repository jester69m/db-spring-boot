package com.example.transactional.employee;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private TransactionTemplate template;
    private EmployeeRepository repository;

    public long count(){
        return repository.count();
    }

    public long countWithReadUncommitted() {
        template.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        template.execute(status -> {
            return count();
        });
        return 0;
    }

    public long countWithReadCommitted() {
        template.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        template.execute(status -> {
            return count();
        });
        return 0;
    }

    public long countWithRepeatableRead() {
        template.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        template.execute(status -> {
            return count();
        });
        return 0;
    }

    public Employee add(Employee employee){
        employee.setId(null);
        return repository.saveAndFlush(employee);
    }
    @Transactional
    public Employee addAndSleep(Employee employee) throws InterruptedException{
        Employee emp = add(employee);
        Thread.sleep(5000);
        return emp;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public List<Employee> getAllWithReadUncommitted() {
        return getAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Employee> getAllWithReadCommitted() {
        return getAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<Employee> getAllWithRepeatableRead() {
        return getAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Employee> getAllWithReadCommittedAndTimeout() throws InterruptedException {
        List<Employee> employees = getAll();
        Thread.sleep(5000);
        return employees;
    }

    public Optional<Employee> getById(Long id) {
        return repository.findById(id);
    }

    public Employee create(Employee employee) {
        employee.setId(null);
        return repository.save(employee);
    }

    public Employee createWithTimeout(Employee employee) throws InterruptedException {
        Employee saved = create(employee);
        Thread.sleep(5000);
        return saved;
    }
    public Optional<Employee> update(Long id, Employee employee) {
        Optional<Employee> emp = repository.findById(id);
        if (emp.isEmpty()) return emp;

        emp.get().setName(employee.getName());
        emp.get().setJobTitle(employee.getJobTitle());
        emp.get().setSalary(employee.getSalary());

        return Optional.of(repository.save(emp.get()));
    }

    public void deleteById(Long id) {
         repository.deleteById(id);
    }
    public void deleteAll() {
        repository.deleteAll();
    }

    public Optional<Employee> updateWithTimeout(Long id, Employee employee) {
        Optional<Employee> update = update(id, employee);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        return update;
    }

    @Transactional(rollbackFor = Exception.class)
    public Employee addWithException(Employee employee) throws Exception {
        employee.setId(null);
        employee = repository.save(employee);
        if (employee.getName() == null) throw new Exception("Exception");
        return employee;
    }
}
