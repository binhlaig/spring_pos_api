create table if not exists shops (
    id bigint primary key,
    shop_code varchar(50) not null unique,
    shop_name varchar(180) not null,
    address text,
    business_type varchar(50) not null,
    status varchar(30) not null default 'TRIAL',
    subscription_plan varchar(50) not null default 'TRIAL',
    subscription_start_date date default current_date,
    subscription_end_date date default ((current_date + interval '14 days')::date),
    suspended_reason text,
    suspended_at timestamptz,
    created_at timestamptz not null default now(),
    updated_at timestamptz
);

insert into shops (
    id,
    shop_code,
    shop_name,
    address,
    business_type,
    status,
    subscription_plan,
    subscription_start_date,
    subscription_end_date,
    created_at
)
select distinct on (u.shop_id)
    u.shop_id,
    trim(u.shop_code),
    coalesce(nullif(trim(u.shop_name), ''), 'Unnamed Shop'),
    u.address,
    coalesce(nullif(trim(cast(u.business_type as text)), ''), 'SUPERMARKET'),
    'TRIAL',
    'TRIAL',
    current_date,
    (current_date + interval '14 days')::date,
    now()
from users u
where u.shop_id is not null
  and u.shop_code is not null
  and trim(u.shop_code) <> ''
  and coalesce(cast(u.roles as text), '') <> 'SUPER_ADMIN'
  and not exists (
      select 1
      from shops s
      where s.shop_code = trim(u.shop_code)
        and s.id <> u.shop_id
  )
order by u.shop_id, u.id
on conflict (id) do update set
    shop_code = excluded.shop_code,
    shop_name = excluded.shop_name,
    address = excluded.address,
    business_type = excluded.business_type,
    updated_at = now();

do $$
begin
    if not exists (
        select 1
        from information_schema.table_constraints
        where constraint_schema = current_schema()
          and table_name = 'users'
          and constraint_name = 'fk_users_shop_id_shops'
    ) then
        alter table users
            add constraint fk_users_shop_id_shops
            foreign key (shop_id) references shops(id);
    end if;
end $$;
