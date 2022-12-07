alter table travel_history add constraint travel_history_member_fk foreign key (member_id) references member(id);
