CREATE TABLE IF NOT EXISTS avg_salary_region(
    region_value VARCHAR(50) NOT NULL,
    avg_salary INT NULL
);
INSERT INTO avg_salary_region (region_value, avg_salary)
        SELECT address, AVG(salary)
        FROM employees
        GROUP BY address;