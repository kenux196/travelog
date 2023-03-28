DROP TABLE IF EXISTS book_point;

CREATE TABLE book_point
(
    id           bigint    NOT NULL auto_increment primary key,
    book_point   tinyint   NOT NULL COMMENT '평점',
    book_id      bigint    NOT NULL COMMENT 'Book FK',
    member_id    bigint    NOT NULL COMMENT 'Member FK',
    created_date timestamp NOT NULL,
    updated_date timestamp NULL
);

ALTER TABLE book_point
    ADD CONSTRAINT book_point_book_fk FOREIGN KEY (book_id) REFERENCES book (id);
ALTER TABLE book_point
    ADD CONSTRAINT book_point_member_fk FOREIGN KEY (member_id) REFERENCES member (id);