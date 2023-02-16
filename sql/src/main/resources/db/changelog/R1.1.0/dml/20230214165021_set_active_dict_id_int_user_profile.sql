-- Author: Поташев Александр Николаевич
-- Description: Set values in column user_profile.active_dict_id

with x as (select id, chat_id from dict)
update user_profile
set active_dict_id = x.id
from x
where user_profile.chat_id = x.chat_id;
