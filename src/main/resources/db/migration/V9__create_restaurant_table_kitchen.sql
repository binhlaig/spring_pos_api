create table if not exists restaurant_tables (
    id bigserial primary key,
    table_no varchar(50) not null,
    table_name varchar(120),
    seats integer,
    status varchar(30) not null default 'FREE',
    floor_name varchar(120),
    note text,
    shop_id bigint not null,
    shop_code varchar(100) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_restaurant_tables_status
        check (status in ('FREE', 'BUSY', 'RESERVED', 'CLEANING'))
);

create unique index if not exists ux_restaurant_tables_shop_table_no
    on restaurant_tables (shop_id, lower(table_no));

create index if not exists idx_restaurant_tables_shop_id
    on restaurant_tables (shop_id);

create index if not exists idx_restaurant_tables_shop_code
    on restaurant_tables (shop_code);

create index if not exists idx_restaurant_tables_status
    on restaurant_tables (status);

create table if not exists kitchen_tickets (
    id bigserial primary key,
    ticket_no varchar(40) not null unique,
    order_type varchar(50) not null,
    table_id bigint,
    table_no varchar(50),
    status varchar(30) not null default 'NEW',
    priority integer not null default 0,
    note text,
    shop_id bigint not null,
    shop_code varchar(100) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_kitchen_tickets_status
        check (status in ('NEW', 'COOKING', 'READY', 'DONE', 'CANCELLED'))
);

create index if not exists idx_kitchen_tickets_shop_id
    on kitchen_tickets (shop_id);

create index if not exists idx_kitchen_tickets_shop_code
    on kitchen_tickets (shop_code);

create index if not exists idx_kitchen_tickets_status
    on kitchen_tickets (status);

create index if not exists idx_kitchen_tickets_created_at
    on kitchen_tickets (created_at);

create table if not exists kitchen_ticket_items (
    id bigserial primary key,
    ticket_id bigint not null references kitchen_tickets(id) on delete cascade,
    menu_item_id bigint,
    item_name varchar(255) not null,
    quantity integer not null default 1,
    unit_price numeric(12,2),
    modifiers text,
    kitchen_note text,
    status varchar(30) not null default 'NEW',
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_kitchen_ticket_items_status
        check (status in ('NEW', 'COOKING', 'READY', 'DONE', 'CANCELLED')),
    constraint chk_kitchen_ticket_items_quantity
        check (quantity > 0)
);

create index if not exists idx_kitchen_ticket_items_ticket_id
    on kitchen_ticket_items (ticket_id);

create index if not exists idx_kitchen_ticket_items_status
    on kitchen_ticket_items (status);
