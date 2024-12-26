create table if not exists student(
    id serial primary key,
    github_id varchar(20) not null unique,
    email varchar(255),
    name varchar(60)
);