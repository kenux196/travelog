drop table if exists member;
create table member
(
    id           bigint not null auto_increment primary key,
    created_date datetime,
    updated_date datetime,
    email        varchar(255) not null,
    name         varchar(255) not null,
    status       varchar(255) not null
);
