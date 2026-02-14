alter table users
    add column if not exists avatar_path varchar(255);
