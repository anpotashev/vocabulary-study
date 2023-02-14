-- Author: Поташев Александр Николаевич
-- Description: Add fk words->dict

alter table words
    add constraint words_dict_fk
        foreign key (dict_id)
            references dict
            on delete cascade
            on update cascade;