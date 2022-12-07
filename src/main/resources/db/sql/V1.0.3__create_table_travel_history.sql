drop table if exists travel_history;
create table travel_history
(
    id             bigint not null auto_increment primary key,
    created_date   timestamp,
    updated_date   timestamp,
    created_by     varchar(255),
    updated_by     varchar(255),
    content        varchar(255),
    end_date       date,
    start_date     date         not null,
    title          varchar(255) not null,
    travel_type    varchar(255) not null,
    destination_id bigint,
    member_id      bigint       not null
);

drop table if exists travel_history_comment;
create table travel_history_comment
(
    id                bigint not null auto_increment primary key,
    comment           varchar(255),
    parent_id         bigint,
    comment_id        bigint,
    travel_history_id bigint not null
);

alter table travel_history
    add constraint travel_history_fk
        foreign key (destination_id)
            references destination(id);

alter table travel_history_comment
    add constraint travel_history_comment_parent_child_fk
        foreign key (comment_id)
            references travel_history_comment(id);

alter table travel_history_comment
    add constraint travel_history_comment_travel_history_fk
        foreign key (travel_history_id)
            references travel_history(id);
