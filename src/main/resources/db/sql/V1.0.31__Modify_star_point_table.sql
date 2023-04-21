ALTER TABLE start_point DROP FOREIGN KEY start_point_fk;
ALTER TABLE start_point DROP COLUMN destination_id;
ALTER TABLE start_point ADD book_id BIGINT NOT NULL;
ALTER TABLE start_point ADD CONSTRAINT start_point_FK FOREIGN KEY (book_id) REFERENCES book(id);
