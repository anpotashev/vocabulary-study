-- Author: Поташев Александр Николаевич
-- Description: Modify table user_profile: add column active_dict_id

alter table user_profile
    add column active_dict_id bigint;

comment on column user_profile.active_dict_id is 'Identifier of active dictionary';

alter table user_profile
    add constraint user_profile_dict_fk
        foreign key (active_dict_id)
            references dict
            on delete set null
            on update cascade;
