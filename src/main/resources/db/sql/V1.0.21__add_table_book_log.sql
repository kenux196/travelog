drop table if exists book_log;
create table book_log
(
    id          bigint not null auto_increment primary key,
    book_status varchar(255),
    end_date    date,
    start_date  date,
    book_id     bigint,
    member_id   bigint
);

alter table book_log
    add constraint book_log_book_fk foreign key (book_id) references book (id);

alter table book_log
    add constraint book_log_member_fk foreign key (member_id) references member (id);