create table if not exists restaurant_orders (
    id bigserial primary key,
    order_no varchar(40) not null unique,
    order_type varchar(50) not null,
    table_id bigint,
    table_no varchar(50),
    staff_id varchar(100),
    staff_name varchar(255),
    subtotal numeric(12,2) default 0,
    service_charge numeric(12,2) default 0,
    tax numeric(12,2) default 0,
    discount numeric(12,2) default 0,
    total numeric(12,2) not null,
    status varchar(30) not null default 'PAID',
    note text,
    shop_id bigint not null,
    shop_code varchar(100) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_restaurant_orders_status
        check (status in ('PAID', 'CANCELLED', 'REFUNDED'))
);

create index if not exists idx_restaurant_orders_shop_id
    on restaurant_orders (shop_id);

create index if not exists idx_restaurant_orders_shop_code
    on restaurant_orders (shop_code);

create index if not exists idx_restaurant_orders_status
    on restaurant_orders (status);

create index if not exists idx_restaurant_orders_created_at
    on restaurant_orders (created_at);

create table if not exists restaurant_order_items (
    id bigserial primary key,
    order_id bigint not null references restaurant_orders(id) on delete cascade,
    product_id bigint,
    item_name varchar(255) not null,
    quantity integer not null default 1,
    unit_price numeric(12,2) not null,
    total_price numeric(12,2) not null,
    modifiers text,
    kitchen_note text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_restaurant_order_items_quantity
        check (quantity > 0)
);

create index if not exists idx_restaurant_order_items_order_id
    on restaurant_order_items (order_id);

create table if not exists restaurant_payments (
    id bigserial primary key,
    order_id bigint not null unique references restaurant_orders(id),
    payment_no varchar(40) not null unique,
    payment_method varchar(50) not null,
    amount numeric(12,2) not null,
    cash_received numeric(12,2),
    change_amount numeric(12,2),
    status varchar(30) not null default 'PAID',
    note text,
    shop_id bigint not null,
    shop_code varchar(100) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_restaurant_payments_status
        check (status in ('PAID', 'CANCELLED', 'REFUNDED'))
);

create index if not exists idx_restaurant_payments_shop_id
    on restaurant_payments (shop_id);

create index if not exists idx_restaurant_payments_shop_code
    on restaurant_payments (shop_code);

create index if not exists idx_restaurant_payments_status
    on restaurant_payments (status);

create index if not exists idx_restaurant_payments_created_at
    on restaurant_payments (created_at);
