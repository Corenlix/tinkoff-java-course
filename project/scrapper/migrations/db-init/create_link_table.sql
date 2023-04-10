create table if not exists link
(
    id  bigint primary key,
    url text not null,
    updated_at timestamp default now() not null
);