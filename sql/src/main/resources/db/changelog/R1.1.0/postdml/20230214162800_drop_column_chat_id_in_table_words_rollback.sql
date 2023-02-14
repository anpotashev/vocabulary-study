-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Delete column words.chat_id

alter table words
    add chat_id bigint not null default -1;

comment on column words.chat_id is 'Telegram chat identifier';

