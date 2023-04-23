CREATE TABLE book_rating (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rating SMALLINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY book_rating_FK (book_id),
    CONSTRAINT book_rating_FK FOREIGN KEY (book_id) REFERENCES book (id)
);
