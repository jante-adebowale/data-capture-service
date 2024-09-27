create table if not exists users
(
    id          varchar(100)  not null primary key,
    firstname   varchar(100) not null,
    lastname    varchar(100) not null,
    email       varchar(45)   unique,
    user_role   varchar(30),
    password    varchar(150) not null,
    enabled     boolean default true,
    modified_at  timestamp without time zone
    );

CREATE TABLE IF NOT EXISTS access_token
(
    id            serial primary key,
    token         text not null,
    refresh_token text ,
    user_id       character varying(50) NOT NULL,
    expired       boolean default false,
    revoked       boolean default false,
    logout        boolean default false,
    logout_date    timestamp without time zone,
    expiry_date    timestamp without time zone
);

CREATE TABLE IF NOT EXISTS captures
(
    id            varchar(100)  not null primary key,
    firstname     varchar(45) not null,
    lastname      varchar(45) not null,
    age     int,
    user_id       varchar(100) not null,
    longitude     text not null,
    latitude      text not null,
    approved      boolean default false,
    entry_date    timestamp without time zone
    );