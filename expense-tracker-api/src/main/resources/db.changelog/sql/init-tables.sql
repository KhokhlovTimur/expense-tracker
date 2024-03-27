create table if not exists users
(
    id       uuid primary key,
    name     varchar(50),
    surname  varchar(50),
    email    varchar(50) unique,
    password varchar(100)
);

create table if not exists banks
(
    id                    uuid primary key,
    name                  varchar(100),
    bic                   varchar(9),
    correspondent_account varchar(20),
    kpp                   varchar(9),
    inn                   varchar(12),
    ogrn                  varchar(13)
);

create table if not exists currencies
(
    id   uuid primary key,
    code varchar(3),
    name varchar(100)
);

create table if not exists expenses
(
    id          uuid primary key,
    user_id     uuid references users (id),
    amount      double precision check ( amount > 0 ),
    time        timestamp,
    currency_id uuid references currencies (id)
);

create table if not exists bank_accounts
(
    account_number varchar(20),
    user_id        uuid references users (id),
    bank_id        uuid references banks (id)
);

create table if not exists expense_categories
(
    id          uuid primary key,
    user_id     uuid references users (id),
    name        varchar(100),
    description varchar(255),
    budget      int check ( budget > 0 )
)