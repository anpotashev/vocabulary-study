-- Author: Aleksandr N Potashev
-- Description: Create table quest_history

create sequence quest_history_seq;
create table quest_history(
    id bigint not null primary key default nextval('quest_history_seq'),
    word_id bigint not null
        references words on delete cascade on update cascade,
    processed bool not null default false
);

comment on table quest_history is 'Table of history questing words';
comment on column quest_history.id is 'Records identifier';
comment on column quest_history.word_id is 'Word identifier';
comment on column quest_history.processed is 'Means if user has already answer to quest';