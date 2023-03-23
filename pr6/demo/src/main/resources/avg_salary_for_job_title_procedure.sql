CREATE OR REPLACE FUNCTION get_employees_avg_salary_by_job_title(job_title_in TEXT)
    RETURNS NUMERIC AS $$
SELECT AVG(salary) FROM employees WHERE job_title = job_title_in;
$$ LANGUAGE SQL;