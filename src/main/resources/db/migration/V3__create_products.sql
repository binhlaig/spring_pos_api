-- 1) create table if not exists (OK)
create table if not exists products (
                                        id bigserial primary key,
                                        sku varchar(64) not null unique,
    product_name varchar(255) not null,
    product_price numeric(12,2) not null,
    product_quantity_amount numeric(12,2) not null default 0,
    barcode varchar(64),
    category varchar(64),
    product_type varchar(32),
    product_discount numeric(12,2) not null default 0,
    note text,
    image_path varchar(512),
    created_at timestamptz not null default now()
    );

-- 2) if table already existed without some columns, add them safely
alter table products add column if not exists product_name varchar(255);
alter table products add column if not exists barcode varchar(64);
alter table products add column if not exists category varchar(64);
alter table products add column if not exists product_type varchar(32);
alter table products add column if not exists product_discount numeric(12,2) not null default 0;
alter table products add column if not exists note text;
alter table products add column if not exists image_path varchar(512);
alter table products add column if not exists created_at timestamptz not null default now();

-- 3) create index safely (Postgres supports IF NOT EXISTS)
create index if not exists idx_products_barcode on products(barcode);
