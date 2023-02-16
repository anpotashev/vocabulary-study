-- Author: Поташев Александр Николаевич
-- Description: Fill dict_id in table words

with x as (select id, chat_id from dict)
update words
set dict_id = x.id
from x
where words.chat_id = x.chat_id;