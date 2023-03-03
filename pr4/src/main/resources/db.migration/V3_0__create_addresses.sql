CREATE TABLE IF NOT EXISTS addresses (
    id DECIMAL PRIMARY KEY ,
    value VARCHAR(50) NOT NULL
);
INSERT INTO addresses (
                      SELECT e.id,e.address FROM employee e
                      );