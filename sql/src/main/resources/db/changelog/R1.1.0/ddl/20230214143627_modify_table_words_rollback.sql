-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Modify table words. Add dict_id column

alter table words drop column dict_id;