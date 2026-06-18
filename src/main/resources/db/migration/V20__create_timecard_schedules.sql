create table if not exists timecard_schedules (
    id bigserial primary key,
    shop_id bigint not null,
    shop_code varchar(50) not null,
    staff_id varchar(50) not null,
    staff_name varchar(180) not null,
    schedule_date date not null,
    start_time time not null,
    end_time time not null,
    role varchar(100),
    note text,
    status varchar(30) not null default 'DRAFT',
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    constraint uk_timecard_schedules_shop_staff_date unique (shop_id, staff_id, schedule_date),
    constraint chk_timecard_schedules_time_order check (end_time > start_time)
);

create index if not exists idx_timecard_schedules_shop_date
    on timecard_schedules (shop_id, schedule_date);
