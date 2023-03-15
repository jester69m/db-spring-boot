package com.testing.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByFirstName(String name);
    List<Employee> findAllByJobTitle(String job_title);
    List<Employee> findAllBySalaryBetween(int min, int max);
}
