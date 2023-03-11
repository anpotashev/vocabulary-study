-- Author: Поташев Александр Николаевич
-- Description: New function get_fibonacci_array

create or replace function get_fibonacci_array(
    p_max_value int
) returns int[]
    language plpgsql
    security definer
as
$$
declare
    l_current_fibonacci_number int   := 0;
    l_next_fibonacci_number    int   := 1;
    l_result                   int[] := array [0];
begin
    while (exists (select from(select (sum(_) - p_max_value) as delta from unnest(l_result) as _) r where r.delta < 0))
        loop
            l_result := array_append(l_result, l_next_fibonacci_number);
            l_next_fibonacci_number := l_current_fibonacci_number + l_next_fibonacci_number;
            l_current_fibonacci_number := l_next_fibonacci_number - l_current_fibonacci_number;
        end loop;
    return l_result;
end;
$$;
