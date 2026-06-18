create table if not exists admin_users (
    id bigserial primary key,
    username varchar not null unique,
    email varchar unique,
    password_hash varchar not null,
    role varchar not null default 'SUPER_ADMIN',
    active boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz null,
    last_login_at timestamptz null
);

insert into admin_users (username, email, password_hash, role, active)
values (
    'superadmin',
    'admin@example.com',
    '$2a$10$jVBh2MQWlFt4NPtg3ze7N.2eZy7pcltvjbzCpK9SXlVQCEcbjrysC',
    'SUPER_ADMIN',
    true
)
on conflict (username) do nothing;
