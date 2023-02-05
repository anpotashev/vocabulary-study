-- Author: Aleksandr N Potashev
-- Description: Create table words

create sequence words_seq;
create table words(
    id bigint primary key default nextval('words_seq'),
    chat_id bigint not null,
    original text not null,
    translated text not null,
    success_results_count bigint not null default 0,
    failed_results_count bigint not null default 0
);

comment on table words is 'Words';
comment on column words.id is 'Record''s identifier';
comment on column words.chat_id is 'Telegram chat''s identifier';
comment on column words.original is 'Original word or expression';
comment on column words.translated is 'Translated word or expression';
comment on column words.success_results_count is 'Count of success answers';
comment on column words.failed_results_count is 'Count of failed answers';