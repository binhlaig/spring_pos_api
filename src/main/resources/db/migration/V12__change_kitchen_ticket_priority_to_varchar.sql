alter table kitchen_tickets
    alter column priority drop default;

alter table kitchen_tickets
    alter column priority type varchar(30)
    using case
        when priority::varchar = '0' then 'NORMAL'
        when priority::varchar = '1' then 'HIGH'
        when priority::varchar = '-1' then 'LOW'
        else upper(priority::varchar)
    end;

alter table kitchen_tickets
    alter column priority set default 'NORMAL';

update kitchen_tickets
set priority = 'NORMAL'
where priority is null or trim(priority) = '';

alter table kitchen_tickets
    alter column priority set not null;
