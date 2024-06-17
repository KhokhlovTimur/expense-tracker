create type user_role_enum as enum ('USER', 'ADMIN');

alter table users add column role user_role_enum;