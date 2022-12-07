alter table password
    add column member_id bigint not null;
alter table password
    add constraint password_member_fk foreign key (member_id)
        references member (id);
