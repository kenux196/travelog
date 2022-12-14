ALTER TABLE member ADD CONSTRAINT member_UN UNIQUE KEY (email);
ALTER TABLE member MODIFY COLUMN status varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
alter table member add column user_role varchar(20) not null;


