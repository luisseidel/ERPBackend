SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--ROLES
create table if not exists public.roles
(
    id   serial primary key,
    name varchar(255) not null
);
create sequence if not exists public.seq_roles increment 1 start 1 owned by roles.id;


--USERS
create table if not exists public.users
(
    id       serial primary key,
    email    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null
);
create sequence if not exists public.seq_users increment 1 start 1 owned by users.id;


--USER ROLES
create table if not exists public.user_roles
(
    id      serial primary key,
    user_id integer not null,
    role_id integer not null,
    CONSTRAINT role_fk FOREIGN KEY (role_id) references roles (id) match simple on update no action on delete no action,
    CONSTRAINT user_fk FOREIGN KEY (user_id) references users (id) match simple on update no action on delete no action,
    CONSTRAINT unique_role_user UNIQUE (user_id, role_id)
);
create sequence if not exists public.seq_user_roles increment 1 start 1 owned by user_roles.id;

--PAISES
create table if not exists public.paises
(
    id      serial primary key,
    nome    varchar(255) not null,
    nome_pt varchar(300) not null,
    iso     varchar(2)   not null
);
create sequence if not exists public.seq_paises increment 1 start 1 owned by paises.id;

--ESTADOS
create table if not exists public.estados
(
    id   serial primary key,
    nome varchar(255) not null,
    uf   varchar(2)   not null,
    ibge integer      not null,
    pais integer      not null references public.paises (id)
);
create sequence if not exists public.seq_estados increment 1 start 1 owned by estados.id;

--CIDADES
create table if not exists public.cidades
(
    id        serial primary key,
    nome      varchar(255),
    estado    integer     not null references public.estados (id),
    ibge      varchar(10) not null,
    latitude  double precision,
    longitude double precision
);
create sequence if not exists public.seq_cidades increment 1 start 1 owned by cidades.id;

--ENDEREÇOS
create table if not exists public.enderecos
(
    id               serial primary key,
    cep              varchar(8)   not null,
    logradouro       varchar(200) not null,
    bairro           varchar(200) null,
    complemento      varchar(200) null,
    ponto_referencia varchar(200) null,
    cidade           integer      not null references public.cidades (id)
);
create sequence if not exists public.seq_enderecos increment 1 start 1 owned by enderecos.id;

--PESSOAS
create table if not exists public.pessoas
(
    id               serial primary key,
    cpf              varchar(11)  not null,
    nome             varchar(255) not null,
    flag_nome_social bool         not null default false,
    nome_social      varchar(255) null,
    sexo             integer      not null default 0,
    id_endereco      integer      null references public.enderecos (id)
);
create sequence if not exists public.seq_pessoas increment 1 start 1 owned by pessoas.id;