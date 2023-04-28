ALTER TABLE users ADD COLUMN realname VARCHAR(255);

CREATE TABLE new_users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    realname VARCHAR(255)
);
INSERT INTO new_users (id, username, email, password, realname)
SELECT id, username, email, password, '' FROM users;