create table if not exists shop_plan_overrides (
    id bigserial primary key,
    shop_id bigint not null references shops(id),
    max_staff int null,
    max_products int null,
    max_receipts_per_month int null,
    max_storage_mb int null,
    max_devices int null,
    note text,
    active boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz null,
    unique (shop_id)
);
