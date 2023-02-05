-- Author: Aleksandr N Potashev
-- Description: Creating table user_profile

create table user_profile(
    chat_id bigint not null primary key,
    locale text not null
);

comment on table user_profile is 'User profile settings table';
comment on column user_profile.chat_id is 'User''s telegram chat identifier';
comment on column user_profile.locale is 'Users''s locale';