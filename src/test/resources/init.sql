create table users
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  text   not null,
    second_name text   not null,
    age         bigint not null
);

create table user_books
(
    id      BIGSERIAL PRIMARY KEY,
    title   text,
    user_id bigint REFERENCES users (id)
);

create table outbox
(
    id   BIGSERIAL PRIMARY KEY,
    data text
);