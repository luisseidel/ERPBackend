--MENUS
create table if not exists public.menu
(
    id   serial primary key,
    name varchar(255) not null,
    url  varchar(255) not null,
    parent_id bigint null references public.menu (id),
    permission_id bigint references permission(id),
    order_position integer default 0,
    active boolean default true,
    home_page boolean default false,
    icon varchar(50),
    description varchar(500)
);
create sequence if not exists public.seq_menu increment 1 start 1 owned by menu.id;

CREATE INDEX idx_menu_parent_id ON public.menu(parent_id);
CREATE INDEX idx_menu_active ON public.menu(active);
CREATE INDEX idx_menu_order ON public.menu(order_position);
CREATE INDEX idx_menu_home_page ON public.menu(home_page);

INSERT INTO public.menu(id, name, url, parent_id, order_position, active, home_page, description)
VALUES
(nextval('seq_menu'), 'Home', '/pages/home', null, null, 0, true, true, 'Página inicial do sistema'),
(nextval('seq_menu'), 'Cadastros', '#', NULL, null, 1, TRUE, FALSE, 'Menu de cadastros gerais'),
(nextval('seq_menu'), 'Relatórios', '#', NULL, null, 2, TRUE, FALSE, 'Menu de relatórios'),
(nextval('seq_menu'), 'Menus', '/pages/menus/consulta', (SELECT id FROM public.menu WHERE name = 'Cadastros'), 1, 1, TRUE, FALSE, 'Gerenciamento de menus do sistema'),
(nextval('seq_menu'), 'Usuários', '/pages/usuarios/consulta', (select id from public.menu where name = 'Cadastros'), 2, 4, true, false, 'Gerenciamento de Usuários'),
(nextval('seq_menu'), 'Roles', '/pages/roles/consulta', (select id from public.menu where name = 'Cadastros'), 3, 3, true, false, 'Gerenciamento de Roles de Usuários'),
(nextval('seq_menu'), 'Cidades', '/pages/cidades/consulta', (select id from public.menu where name = 'Cadastros'), 4, 4, true, false, 'Gerenciamento de Permissões'),
(nextval('seq_menu'), 'Estados', '/pages/estados/consulta', (select id from public.menu where name = 'Cadastros'), 5, 5, true, false, 'Gerenciamento de Estados'),
(nextval('seq_menu'), 'Cidades', '/pages/cidades/consulta', (select id from public.menu where name = 'Cadastros'), 6, 6, true, false, 'Gerenciamento de Cidades'),
(nextval('seq_menu'), 'Endereços', '/pages/enderecos/consulta', (select id from public.menu where name = 'Cadastros'), 7, 7, true, false, 'Gerenciamento de Endereços')
;