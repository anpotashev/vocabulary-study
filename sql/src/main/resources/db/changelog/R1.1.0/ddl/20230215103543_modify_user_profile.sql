-- Author: Поташев Александр Николаевич
-- Description: Modify table user_profile: add column simple_message_data

alter table user_profile
    add column simple_message_data_id bigint;

comment on column user_profile.simple_message_data_id is 'Identifier of current data for processing simple message';

alter table user_profile
    add constraint user_profile_simple_message_data_fk
        foreign key (simple_message_data_id)
            references simple_message_data
            on delete set null
            on update cascade;
