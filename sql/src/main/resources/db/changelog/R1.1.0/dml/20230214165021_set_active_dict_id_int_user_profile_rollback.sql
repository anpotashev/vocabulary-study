-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Set values in column user_profile.active_dict_id

update user_profile set active_dict_id = null;