-- Author: Поташев Александр Николаевич
-- Description: Create new table dict

create sequence dict_seq;
create table dict(
    id bigint not null primary key default nextval('dict_seq'),
    chat_id bigint not null
        references user_profile on delete cascade on update cascade,
    name text not null
);

comment on table dict is 'Dictionary';
comment on column dict.id is 'Record identifier';
comment on column dict.chat_id is 'User telegram chat identifier';
comment on column dict.name is 'Dictionary name';