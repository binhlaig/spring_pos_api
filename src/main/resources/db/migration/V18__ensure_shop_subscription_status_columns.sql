alter table shops add column if not exists status varchar(30) not null default 'TRIAL';
alter table shops add column if not exists subscription_plan varchar(50) not null default 'TRIAL';
alter table shops add column if not exists subscription_start_date date default current_date;
alter table shops add column if not exists subscription_end_date date default ((current_date + interval '14 days')::date);
