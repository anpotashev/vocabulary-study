-- Author: Поташев Александр Николаевич
-- Description: fill data in the dict table for present users

insert into dict(chat_id, name)
select chat_id, 'dictionary-1' from user_profile;