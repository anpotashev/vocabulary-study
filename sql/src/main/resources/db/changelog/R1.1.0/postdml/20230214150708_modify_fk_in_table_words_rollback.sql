-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Add fk words->dict, remove fk words->user_profile

alter table words drop constraint words_dict_fk;