-- V1.6: inserir menus pais com IDs fixos
INSERT INTO public.menu(id, name, url, parent_id, permission_id, order_position, active, home_page, icon, description)
VALUES
(nextval('seq_menu'), 'Home', '/pages/home', NULL, NULL, 0, TRUE, TRUE, NULL, 'Página inicial do sistema'),
(nextval('seq_menu'), 'Cadastros', '#', NULL, NULL, 1, TRUE, FALSE, NULL, 'Menu de cadastros gerais'),
(nextval('seq_menu'), 'Relatórios', '#', NULL, NULL, 2, TRUE, FALSE, NULL, 'Menu de relatórios')
;

ALTER SEQUENCE seq_menu RESTART 4;