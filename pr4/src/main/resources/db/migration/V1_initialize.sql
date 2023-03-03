CREATE TABLE IF NOT EXISTS employees (
    id DECIMAL PRIMARY KEY ,
    first_name VARCHAR(30) NOT NULL ,
    second_name VARCHAR(30) NOT NULL ,
    job_title VARCHAR(30) NOT NULL ,
    salary DECIMAL(13,4) NOT NULL,
    phone_number VARCHAR(13) NOT NULL,
    address VARCHAR(100) NOT NULL
);
INSERT INTO employees(id, first_name, second_name, job_title, salary, phone_number, address) VALUES
(0,'firstName1','secondName1','manager',25000,'+380678372633','Ukraine, Kyiv'),
(2,'firstName2','secondName2','director',45000,'+380958372931','Ukraine, Kyiv'),
(4,'firstName3','secondName3','manager',22000,'+380958375682','Ukraine, Kyiv'),
(6,'firstName4','secondName4','manager',27000,'+380978383674','Ukraine, Lviv'),
(8,'firstName5','secondName5','manager',30000,'+380678341665','Ukraine, Lviv'),
(10,'firstName6','secondName6','director',27000,'+380678341665','Ukraine, Dnipro'),
(12,'firstName7','secondName7','manager',21000,'+380678341665','Ukraine, Dnipro'),
(14,'firstName8','secondName8','manager',19000,'+380678341665','Ukraine, Dnipro'),
(16,'firstName9','secondName9','manager',24000,'+380678341665','Ukraine, Dnipro')
;
