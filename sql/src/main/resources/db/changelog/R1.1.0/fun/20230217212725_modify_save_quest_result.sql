-- Author: Поташев Александр Николаевич
-- Description: Modify function save_quest_result

create or replace function save_quest_result(
    p_err_code out text,
    p_history_id in bigint,
    p_success in bool
)
    language plpgsql
    security definer
as
$$
declare
    l_word_id bigint;
begin

    if (exists(select from quest_history where id = p_history_id and processed)) then
        p_err_code := 'QUEST_ALREADY_PROCESSED';
        return;
    end if;

    select word_id into l_word_id from quest_history where id = p_history_id;

    if (l_word_id is null) then
        p_err_code := 'WORD_NOT_FOUND';
        return;
    end if;

    if (p_success) then
        update words
        set success_results_count = success_results_count + 1
        where id = l_word_id;
    end if;

    if (not p_success) then
        update words
        set failed_results_count = failed_results_count + 1
        where id = l_word_id;
    end if;

    update quest_history
    set processed = true
    where id = p_history_id;

    return;
exception
    when others then
        p_err_code := 'UNKNOWN_ERROR';
end;
$$;