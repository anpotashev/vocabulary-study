-- Rollback --
-- Author: Поташев Александр Николаевич
-- Description: Modify function quest_new_word

create or replace function quest_new_word(
    p_err_code out text,
    p_out out jsonb,
    p_chat_id in bigint
)
    language plpgsql
    security definer
as
$$
declare
    l_min_success     int;
    l_max_success     int;
    l_random          int;
    l_word_id         bigint;
    l_word_history_id bigint;
begin
    if (not exists(select from words where chat_id = p_chat_id)) then
        p_err_code := 'WORDS_NOT_PRESENT';
        return;
    end if;

    select min(w.success_results_count - w.failed_results_count),
           max(w.success_results_count - w.failed_results_count)
    into l_min_success, l_max_success
    from words w
    where chat_id = p_chat_id;

    l_random := floor(random() * (l_max_success - l_min_success + 1) + l_min_success);

    select r.id
    into l_word_id
    from (select id, random() as _random
          from words
          where success_results_count - words.failed_results_count <= l_random) r
    order by _random
    limit 1;

    insert into quest_history(word_id) values (l_word_id) returning id into l_word_history_id;

    select data || jsonb_build_object('historyId', l_word_history_id)
    into p_out
    from quest_data
    where id = l_word_id;
    return;
exception
    when others then
        p_err_code := 'UNKNOWN_ERROR';
end;
$$;