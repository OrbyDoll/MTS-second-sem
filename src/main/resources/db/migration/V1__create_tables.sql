create table users (
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(50) not null,
    second_name varchar(50) not null,
    age bigint not null
);

create table user_books (
    id BIGSERIAL PRIMARY KEY,
    title varchar(50),
    user_id bigint REFERENCES users(id)
);