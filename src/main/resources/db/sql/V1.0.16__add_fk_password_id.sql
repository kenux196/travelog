ALTER TABLE password DROP FOREIGN KEY password_member_fk;
alter table password drop column member_id;
ALTER TABLE member ADD CONSTRAINT member_FK FOREIGN KEY (password_id) REFERENCES password(id);
