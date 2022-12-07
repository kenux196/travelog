drop table if exists start_point;
create table start_point
(
    id             bigint not null auto_increment primary key,
    star_point     int,
    destination_id bigint not null
);

drop table if exists destination;
create table destination
(
    id          bigint not null auto_increment primary key,
    address     varchar(255),
    information varchar(255),
    name        varchar(255)
);

alter table start_point add constraint start_point_fk foreign key (destination_id) references destination(id);

drop table if exists password;
create table password
(
    member_name  varchar(255) not null primary key,
    created_date timestamp,
    updated_date timestamp,
    password     varchar(255)
);
