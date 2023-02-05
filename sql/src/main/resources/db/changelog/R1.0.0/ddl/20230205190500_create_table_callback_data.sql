-- Author: Aleksandr N Potashev
-- Description: Create new table callback_data

create sequence callback_data_seq;
create table callback_data(
    id bigint not null primary key default nextval('callback_data_seq'),
    callback_name text not null,
    callback_data jsonb not null
);

comment on table callback_data is 'Table for keeping callback-data';
comment on column callback_data.id is 'Record''s identifier';
comment on column callback_data.callback_name is 'Name of service that handle this callback';
comment on column callback_data.callback_data is 'Parameters map, used by callback handler';
