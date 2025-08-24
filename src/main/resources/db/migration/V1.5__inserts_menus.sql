--MENUS
create table if not exists public.menu
(
    id   serial primary key,
    name varchar(255) not null,
    url  varchar(255) not null,
    parent_id bigint null references public.menu (id),
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

insert into public.menu(id, name, url, parent_id, order_position, active, home_page, description)
values (nextval('seq_menu'), 'Home', '/pages/home', null, 0, true, true, 'Página inicial do sistema');
insert into public.menu(id, name, url, parent_id, order_position, active, home_page, description)
values (nextval('seq_menu'), 'Cidades', '/pages/cidades/consulta', null, 1, true, false, 'Gerenciamento de cidades');
insert into public.menu(id, name, url, parent_id, order_position, active, home_page, description)
values (nextval('seq_menu'), 'Estados', '/pages/estados/consulta', null, 2, true, false, 'Gerenciamento de estados');

-- Inserir menu para gerenciar menus
INSERT INTO public.menu(id, name, url, parent_id, order_position, active, home_page, description)
VALUES (nextval('seq_menu'), 'Menus', '/pages/menus/consulta', 
        (SELECT id FROM public.menu WHERE name = 'Cadastros'), 1, TRUE, FALSE, 'Gerenciamento de menus do sistema');


-- Inserir alguns submenus de exemplo
INSERT INTO public.menu(id, name, url, parent_id, order_position, active, home_page, description)
VALUES 
(nextval('seq_menu'), 'Cadastros', '#', NULL, 4, TRUE, FALSE, 'Menu de cadastros gerais'),
(nextval('seq_menu'), 'Relatórios', '#', NULL, 5, TRUE, FALSE, 'Menu de relatórios');

-- Mover Cidades e Estados para submenu Cadastros
UPDATE public.menu SET parent_id = (SELECT id FROM public.menu WHERE name = 'Cadastros') 
WHERE name IN ('Cidades', 'Estados');