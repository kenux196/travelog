DROP TABLE IF EXISTS book_review;

CREATE TABLE book_review
(
    id           bigint        NOT NULL auto_increment primary key,
    review       varchar(1000) NOT NULL COMMENT '한줄평 1000자 이내',
    rating       integer       NOT NULL COMMENT '별점',
    book_id      bigint        NOT NULL COMMENT 'Book FK',
    member_id    bigint        NOT NULL COMMENT 'Member FK',
    created_date timestamp     NOT NULL COMMENT '작성일',
    updated_date timestamp     NULL COMMENT '수정일'
);

ALTER TABLE book_review
    ADD CONSTRAINT book_review_book_fk foreign key (book_id) references book (id);
alter table book_review
    add constraint book_review_member_fk foreign key (member_id) references member (id);

