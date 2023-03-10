CREATE TABLE IF NOT EXISTS employee (
                                        id BIGSERIAL NOT NULL ,
                                        first_name VARCHAR(50) NOT NULL ,
                                        second_name VARCHAR(50) NOT NULL ,
                                        job_title VARCHAR(50) NOT NULL ,
                                        salary INTEGER NOT NULL ,
                                        phone_number VARCHAR(13) NOT NULL ,
                                        address VARCHAR(100),
                                        PRIMARY KEY (id)
);