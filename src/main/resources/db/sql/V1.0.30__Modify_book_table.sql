alter table book
    add column rate float default 0;

alter table book_review
    change rating rate integer;