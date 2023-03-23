package com.jdbc.template.demo.employee.repository;

import com.jdbc.template.demo.employee.entity.Employee;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper mapper;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        mapper = new EmployeeRowMapper();
    }

    @Transactional
    public Employee create(final Employee employee) {
        final String sql = "INSERT INTO employees(first_name, second_name, job_title, salary, phone_number) VALUES (?,?,?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getSecondName());
            ps.setString(3, employee.getJobTitle());
            ps.setInt(4, employee.getSalary());
            ps.setString(5, employee.getPhoneNumber());
            return ps;
        }, holder);

        long ratingId = (Long) holder.getKeyList().get(0).get("id");
        employee.setId(ratingId);

        return employee;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        String selectQuery = "SELECT * FROM employees";
        return jdbcTemplate.query(selectQuery, mapper);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        String selectQuery = "SELECT * FROM employees WHERE id=?";
        List<Employee> list = jdbcTemplate.query(selectQuery, mapper, id);
        if (list.isEmpty())
            return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Transactional
    public void update(Employee emp) {
        String updateQuery = "UPDATE employees SET first_name = ?, second_name = ?, job_title = ?, salary = ?, phone_number = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery, emp.getFirstName(), emp.getSecondName(), emp.getJobTitle(), emp.getSalary(), emp.getPhoneNumber(), emp.getId());
    }

    public double getAvgSalaryByJobTitle(String jobTitle) {
        String sql = "SELECT get_employees_avg_salary_by_job_title(?)";
        double avgSalary = jdbcTemplate.queryForObject(sql, new Object[]{jobTitle}, Double.class);
        return avgSalary;
    }

    public void batchUpdate(List<Employee> employees) {
        String sql = "UPDATE employees SET first_name = ?, second_name = ?, job_title = ?, salary = ?, phone_number = ? WHERE id = ?";
        int[] updateCounts = jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, employees.get(i).getFirstName());
                        ps.setString(2, employees.get(i).getSecondName());
                        ps.setString(3, employees.get(i).getJobTitle());
                        ps.setInt(4, employees.get(i).getSalary());
                        ps.setString(5, employees.get(i).getPhoneNumber());
                        ps.setLong(6, employees.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return employees.size();
                    }
                });
    }

    @Transactional
    public void deleteById(Long id) {
        String deleteQuery = "DELETE FROM employees WHERE id=?";
        jdbcTemplate.update(deleteQuery, id);
    }

    @Transactional
    public void delete(@NotNull Collection<Employee> list) {
        for (Employee e : list) {
            deleteById(e.getId());
        }
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM employees");
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee emp = new Employee();
            emp.setId(rs.getLong("id"));
            emp.setFirstName(rs.getString("first_name"));
            emp.setSecondName(rs.getString("second_name"));
            emp.setJobTitle(rs.getString("job_title"));
            emp.setSalary(rs.getInt("salary"));
            emp.setPhoneNumber(rs.getString("phone_number"));
            return emp;
        }
    }
}
