-- Author: Поташев Александр Николаевич
-- Description: Create table simple_message_data

create sequence simple_message_data_seq;
create table simple_message_data(
    id bigint not null primary key default nextval('simple_message_data_seq'),
    handler_name text not null,
    payload jsonb not null
);

comment on table simple_message_data is 'Table to keeping data for handling simple message';
comment on column simple_message_data.id is 'Record identifier';
comment on column simple_message_data.handler_name is 'Name of service that handle next simple (not command not callback) telegram update ';
comment on column simple_message_data.payload is 'Payload used by handler';
