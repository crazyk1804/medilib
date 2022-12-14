drop table if exists users;
create table users (
    id bigint auto_increment primary key,
    email varchar(30),
    password varchar(30)
);