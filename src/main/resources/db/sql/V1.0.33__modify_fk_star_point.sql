ALTER TABLE test.book_start_point ADD CONSTRAINT book_start_point_FK FOREIGN KEY (book_id) REFERENCES test.book(id);
ALTER TABLE test.book_start_point DROP FOREIGN KEY start_point_FK;
