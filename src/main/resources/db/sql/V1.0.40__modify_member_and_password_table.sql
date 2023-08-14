ALTER TABLE password ADD member_id BIGINT;

ALTER TABLE `member` DROP FOREIGN KEY member_FK;

UPDATE password p
set p.member_id = (SELECT m.id from `member` m where p.id = m.password_id);

ALTER TABLE password ADD CONSTRAINT password_member_FK FOREIGN KEY (member_id) REFERENCES `member`(id);
