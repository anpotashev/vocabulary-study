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
    l_active_dict_id  bigint;
    l_fibonacci_array int[];
    l_max_value int;
begin
    select active_dict_id
    into l_active_dict_id
    from user_profile
    where chat_id = p_chat_id;

    if (l_active_dict_id is null) then
        p_err_code := 'ACTIVE_DICTIONARY_NOT_SET';
        return;
    end if;

    if (not exists(select
                   from words
                   where dict_id = l_active_dict_id)) then
        p_err_code := 'WORDS_NOT_PRESENT';
        return;
    end if;

    select min(w.success_results_count - w.failed_results_count),
           max(w.success_results_count - w.failed_results_count)
    into l_min_success, l_max_success
    from words w
    where dict_id = l_active_dict_id;

    l_fibonacci_array := get_fibonacci_array(l_max_success - l_min_success);
    l_random := floor(random() * array_length(l_fibonacci_array, 1) + 1);
    select sum(_) + l_min_success into l_max_value from unnest((l_fibonacci_array)[0:l_random]) _;

    select r.id
    into l_word_id
    from (select id, random() as _random
          from words
          where success_results_count - words.failed_results_count <= l_max_value
            and dict_id = l_active_dict_id
         ) r
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
