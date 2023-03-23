DROP TABLE employees;
CREATE TABLE IF NOT EXISTS employees (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          second_name VARCHAR(50) NOT NULL,
                          job_title VARCHAR(50) NOT NULL,
                          salary INT CHECK (salary >= 0),
                          phone_number VARCHAR(15) NOT NULL
);