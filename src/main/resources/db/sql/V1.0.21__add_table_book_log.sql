drop table if exists book_log;
create table book_log
(
    id bigint not null auto_increment primary key,
    title varchar(255) not null,
    authors varchar(255) not null,
    content varchar(1000),
    isbn varchar(20) not null,
    price int,
    published_date date not null,
    publisher varchar(255) not null,
    thumbnail varchar(255)
);

alter table book_log add column book_id bigint not null;
alter table book_log add constraint book_log_book_fk foreign key (book_id) references book (id);

alter table book_log add column member_id bigint not null;
alter table book_log add constraint book_log_member_fk foreign key (member_id) references member (id);