alter table travel_log
    drop foreign key travel_history_fk;

alter table travel_log
    add constraint travel_log_fk
        foreign key (destination_id) references destination (id);

alter table travel_log
    drop foreign key travel_history_member_fk;

alter table travel_log
    add constraint travel_log_member_fk
        foreign key (member_id) references member (id);

alter table travel_log_comment
    drop foreign key travel_history_comment_parent_child_fk;

alter table travel_log_comment
    add constraint travel_log_comment_child_fk
        foreign key (comment_id) references travel_log_comment (id);

alter table travel_log_comment
    drop foreign key travel_history_comment_parent_fk;

alter table travel_log_comment
    add constraint travel_log_comment_parent_fk
        foreign key (parent_id) references travel_log_comment (id);

alter table travel_log_comment
    drop foreign key travel_history_comment_travel_history_fk;

alter table travel_log_comment
    add constraint travel_log_comment_travel_log_fk
        foreign key (travel_history_id) references travel_log (id);
