INSERT INTO project (id, name)
VALUES ('0', 'proj0'),
       ('1', 'proj1'),
       ('2', 'proj2');

INSERT INTO department (id, name)
VALUES ('0', 'dep0'),
       ('1', 'dep1');

INSERT INTO employee (id, firstName, secondName, department_id)
VALUES ('0', 'name01','name02', '0'),
       ('1', 'name11','name12', '1');

INSERT INTO employee_project (project_id, employee_id)
VALUES ('0', '0'),
       ('1', '1'),
       ('2', '1'),
       ('1', '0');


SELECT *
from employee;

SELECT *
FROM employee
WHERE id IN (SELECT employee_id
             FROM employee_project
             WHERE project_id = '1');

SELECT id, firstName, secondName,gender, department.name as department
FROM employee
         NATURAL JOIN department;

SELECT *
FROM employee
         NATURAL JOIN department
         JOIN employee_project on employee.id = employee_project.employee_id;


SELECT e.id, firstName, secondName,gender, p.projects, d.name as department
FROM employee e
         JOIN (SELECT employee_id,
                      string_agg((SELECT name
                                  FROM project
                                  WHERE id = s.project_id), ', ') as projects
               FROM employee_project s
               GROUP BY employee_id) p ON e.id = p.employee_id
         JOIN department d ON e.department_id = d.id;

