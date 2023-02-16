-- Author: Поташев Александр Николаевич
-- Description: Modify table words. Add dict_id column

alter table words add column dict_id bigint not null default -1;

comment on column words.dict_id is 'Dictionary identifier';