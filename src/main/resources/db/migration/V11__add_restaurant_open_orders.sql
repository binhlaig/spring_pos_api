alter table restaurant_orders
    drop constraint if exists chk_restaurant_orders_status;

alter table restaurant_orders
    add constraint chk_restaurant_orders_status
        check (status in ('OPEN', 'PAID', 'CANCELLED', 'REFUNDED'));

create unique index if not exists ux_restaurant_orders_open_shop_table
    on restaurant_orders (shop_id, table_id)
    where status = 'OPEN' and table_id is not null;
