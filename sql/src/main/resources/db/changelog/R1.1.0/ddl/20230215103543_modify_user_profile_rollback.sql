-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Modify table user_profile: add column simple_message_data

alter table user_profile
    drop constraint user_profile_dict_fk;

alter table user_profile
    drop column active_dict_id;
