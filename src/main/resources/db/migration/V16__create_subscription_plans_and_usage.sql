create table if not exists subscription_plans (
    id bigserial primary key,
    code varchar(50) not null unique,
    name varchar(100) not null,
    price_monthly numeric(12,2) not null default 0,
    max_staff int null,
    max_products int null,
    max_receipts_per_month int null,
    max_storage_mb int null,
    max_devices int null,
    max_branches int null,
    allow_restaurant boolean not null default false,
    allow_fashion boolean not null default false,
    allow_analytics boolean not null default false,
    allow_kitchen boolean not null default false,
    allow_table_order boolean not null default false,
    active boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz null
);

insert into subscription_plans (
    code, name, price_monthly, max_staff, max_products, max_receipts_per_month,
    max_storage_mb, max_devices, max_branches, allow_restaurant, allow_fashion,
    allow_analytics, allow_kitchen, allow_table_order
) values
    ('TRIAL', 'Trial', 0, 2, 100, 300, 500, 1, 1, false, false, false, false, false),
    ('BASIC', 'Basic', 50000, 3, 500, 3000, 1024, 2, 1, false, true, false, false, false),
    ('STANDARD', 'Standard', 100000, 10, 5000, 30000, 5120, 5, 1, true, true, true, true, false),
    ('PREMIUM', 'Premium', 200000, null, null, null, 20480, null, null, true, true, true, true, true)
on conflict (code) do nothing;

create table if not exists shop_usage_monthly (
    id bigserial primary key,
    shop_id bigint not null references shops(id),
    year int not null,
    month int not null,
    staff_count int not null default 0,
    product_count int not null default 0,
    receipt_count int not null default 0,
    storage_used_mb int not null default 0,
    device_count int not null default 0,
    updated_at timestamptz not null default now(),
    unique (shop_id, year, month)
);

alter table shops add column if not exists subscription_plan varchar(50) not null default 'TRIAL';
alter table shops add column if not exists subscription_start_date date default current_date;
alter table shops add column if not exists subscription_end_date date default ((current_date + interval '14 days')::date);
