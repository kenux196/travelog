alter table refresh_token drop constraint refresh_token_member_fk;
alter table refresh_token drop column member_id;
alter table refresh_token add column email varchar(255) not null;