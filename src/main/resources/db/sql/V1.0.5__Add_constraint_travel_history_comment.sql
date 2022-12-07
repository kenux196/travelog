alter table travel_history_comment
    add constraint travel_history_comment_parent_fk
        foreign key (parent_id)
            references travel_history_comment(id);
