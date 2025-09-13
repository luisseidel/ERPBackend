SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--ROLES
create table if not exists public.role
(
    id   serial primary key,
    name varchar(255) not null,
    active boolean default true
);
create sequence if not exists public.seq_role increment 1 start 1 owned by role.id;


--USERS
create table if not exists public.usuario
(
    id       serial primary key,
    email    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null,
    enabled  boolean not null default true,
    account_expired     boolean not null default false,
    account_locked      boolean not null default false,
    credentials_expired boolean not null default false
);
create sequence if not exists public.seq_usuario increment 1 start 1 owned by usuario.id;


--USER ROLES
create table if not exists public.user_role
(
    id      serial primary key,
    user_id integer not null,
    role_id integer not null,
    CONSTRAINT role_fk FOREIGN KEY (role_id) references role (id) match simple on update no action on delete no action,
    CONSTRAINT user_fk FOREIGN KEY (user_id) references usuario (id) match simple on update no action on delete no action,
    CONSTRAINT unique_role_user UNIQUE (user_id, role_id)
);
create sequence if not exists public.seq_user_role increment 1 start 1 owned by user_role.id;

--PERMISSIONS
create table if not exists public.permission(
    id  bigserial primary key,
    name varchar not null unique,
    description varchar(255) null
);
create sequence if not exists public.seq_permission increment 1 start 1 owned by permission.id;

create table if not exists public.role_permission(
    id  bigserial not null primary key,
    role_id bigint not null references role(id),
    permission_id bigint not null references permission(id),
    consultar boolean default false,
    adicionar boolean default false,
    editar boolean default false,
    excluir boolean default false
);
create sequence if not exists public.seq_role_permission increment 1 start 1 owned by role_permission.id;


--PAISES
create table if not exists public.pais
(
    id      serial primary key,
    nome    varchar(255) not null,
    nome_pt varchar(300) not null,
    sigla varchar(2) null
);
create sequence if not exists public.seq_pais increment 1 start 1 owned by pais.id;

--ESTADOS
create table if not exists public.estado
(
    id   serial primary key,
    nome varchar(255) not null,
    uf   varchar(2)   not null,
    ibge integer      not null,
    pais integer null references public.pais (id)
);
create sequence if not exists public.seq_estado increment 1 start 1 owned by estado.id;

--CIDADES
create table if not exists public.cidade
(
    id        serial primary key,
    nome      varchar(255),
    estado integer not null references public.estado (id),
    ibge      varchar(10) not null,
    latitude  double precision,
    longitude double precision
);
create sequence if not exists public.seq_cidade increment 1 start 1 owned by cidade.id;

--ENDEREÇOS
create table if not exists public.endereco
(
    id               serial primary key,
    cep              varchar(8)   not null,
    logradouro       varchar(200) not null,
    numero varchar(10) null,
    bairro           varchar(200) null,
    complemento      varchar(200) null,
    ponto_referencia varchar(200) null,
    cidade integer not null references public.cidade (id)
);
create sequence if not exists public.seq_endereco increment 1 start 1 owned by endereco.id;

--PESSOAS
create table if not exists public.pessoa
(
    id               serial primary key,
    cpf_cnpj varchar(14) not null,
    nome             varchar(255) not null,
    sexo             integer      not null default 0,
    email varchar(255) null,
    id_endereco integer     null references public.endereco (id)
);
create sequence if not exists public.seq_pessoa increment 1 start 1 owned by pessoa.id;