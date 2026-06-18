create table if not exists shop_features (
    id bigserial primary key,
    shop_id bigint not null unique,
    shop_code varchar(50) not null,
    dashboard_enabled boolean default true,
    products_enabled boolean default true,
    pos_register_enabled boolean default true,
    receipts_enabled boolean default true,
    staff_enabled boolean default false,
    tasks_enabled boolean default false,
    timecard_enabled boolean default false,
    restaurant_pos_enabled boolean default false,
    restaurant_tables_enabled boolean default false,
    restaurant_kitchen_enabled boolean default false,
    restaurant_orders_enabled boolean default false,
    settings_enabled boolean default true,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
