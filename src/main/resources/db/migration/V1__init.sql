


-- USERS
create table if not exists users (
                                     id bigserial primary key,
                                     username varchar(80) not null unique,
    password_hash varchar(255) not null,
    active boolean not null default true,
    created_at timestamptz not null default now()
    );

create table if not exists user_roles (
                                          user_id bigint not null references users(id) on delete cascade,
    role varchar(30) not null,
    primary key (user_id, role)
    );

-- PRODUCTS
create table if not exists products (
                                        id bigserial primary key,
                                        sku varchar(64) unique,
    name varchar(255) not null,
    price numeric(12,2) not null default 0,
    cost numeric(12,2) not null default 0,
    stock integer not null default 0,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
    );
