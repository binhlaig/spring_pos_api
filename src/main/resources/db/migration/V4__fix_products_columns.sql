alter table products add column if not exists product_name varchar(255);
alter table products add column if not exists product_price numeric(12,2);
alter table products add column if not exists product_quantity_amount numeric(12,2);
