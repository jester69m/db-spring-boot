package com.example.transactional.employee;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Employee create(Employee employee){
        return repository.save(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<Employee> findAll(){
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Optional<Employee> find(Long id) {
        return repository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Employee update(Long id, Employee newEmp) {
        Optional<Employee> check = find(id);
        if(check.isEmpty()){
            return null;
        }
        Employee emp = check.get();
        emp.setFirstName(newEmp.getFirstName());
        emp.setSecondName(newEmp.getSecondName());
        emp.setJobTitle(newEmp.getJobTitle());
        emp.setSalary(newEmp.getSalary());
        emp.setPhoneNumber(newEmp.getPhoneNumber());

        return repository.save(emp);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        repository.delete(find(id).get());
    }

    public void increaseSalary(Long employeeId, int amount) {
        Employee employee = repository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        employee.setSalary(employee.getSalary() + amount);
        repository.save(employee);
    }





}
