alter table PUBLIC.TRAVEL_HISTORY add constraint travel_history_member_fk foreign key (id) references PUBLIC."MEMBER"(id);
