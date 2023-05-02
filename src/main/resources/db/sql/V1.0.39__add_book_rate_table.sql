DROP TABLE book_rating;

CREATE TABLE book_rate
(
    id           BIGINT    NOT NULL AUTO_INCREMENT,
    rate         SMALLINT  NOT NULL,
    book_id      BIGINT    NOT NULL,
    member_id    BIGINT    NOT NULL,
    created_date timestamp not null,
    updated_date timestamp not null,
    PRIMARY KEY (id),
    KEY book_rate_book_fk (book_id),
    CONSTRAINT book_rate_book_fk FOREIGN KEY (book_id) REFERENCES book (id),
    KEY book_rate_member_fk (member_id),
    CONSTRAINT book_rate_member_fk FOREIGN KEY (member_id) REFERENCES member (id)
);
