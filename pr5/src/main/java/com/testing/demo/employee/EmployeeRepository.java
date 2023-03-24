package com.testing.demo.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByNameIgnoreCase(String name);
    List<Employee> findAllByJob(String job_title);
    List<Employee> findAllBySalaryBetween(int min, int max);
}
