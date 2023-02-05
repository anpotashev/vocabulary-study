-- Author: Aleksandr N Potashev
-- Description: Create view quest_data

create view quest_data as
select id, to_jsonb(r) as data
from (select
          id,
          original,
          translated
      from words) r;

comment on view quest_data is 'Technical view with jsonb-data for mapping to class QuestData';
comment on column quest_data.id is 'Identifier from table words';
comment on column quest_data.data is 'data from words collected to jsonb';
