ALTER TABLE book_start_point ADD CONSTRAINT book_start_point_FK FOREIGN KEY (book_id) REFERENCES book(id);
ALTER TABLE book_start_point DROP FOREIGN KEY start_point_FK;
