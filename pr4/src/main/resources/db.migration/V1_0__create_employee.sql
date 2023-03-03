drop table if exists employee cascade;

create table employee (
    id bigint not null,
    address varchar(255),
    first_name varchar(255) not null,
    job_title varchar(255) not null,
    phone_number varchar(255),
    salary integer not null,
    second_name varchar(255) not null,
    primary key (id)
);

