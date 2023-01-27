drop table if exists refresh_token;
create table refresh_token
(
    id           bigint not null auto_increment primary key,
    token        varchar(255) not null,
    member_id    bigint not null
);
alter table refresh_token add constraint refresh_token_member_fk foreign key (member_id) references member(id);
