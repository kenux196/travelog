create table member
(
    id           bigint generated by default as identity,
    created_date timestamp,
    updated_date timestamp,
    email        varchar(255),
    name         varchar(255),
    primary key (id)
);