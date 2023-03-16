drop table if exists book;
create table book
(
    id bigint not null auto_increment primary key,
    title varchar(255) not null,
    authors varchar(255) not null,
    content TEXT,
    isbn varchar(20) not null,
    price integer,
    published_date date not null,
    publisher varchar(255) not null,
    thumbnail varchar(255)
);