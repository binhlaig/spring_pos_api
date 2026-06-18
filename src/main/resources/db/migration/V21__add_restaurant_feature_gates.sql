alter table shop_features
    add column if not exists allow_restaurant boolean default true,
    add column if not exists allow_kitchen boolean default true,
    add column if not exists allow_table_order boolean default true;

update shop_features
set allow_restaurant = coalesce(allow_restaurant, true),
    allow_kitchen = coalesce(allow_kitchen, true),
    allow_table_order = coalesce(allow_table_order, true);

update shop_features
set allow_restaurant = true,
    allow_kitchen = true,
    allow_table_order = true
where shop_id = 3536
   or upper(shop_code) = 'SHP-47E';
