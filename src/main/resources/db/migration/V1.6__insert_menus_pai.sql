-- V1.6: inserir menus pais com IDs fixos
INSERT INTO public.menu(id, name, url, parent_id, permission_id, order_position, active, home_page, description)
VALUES
(1, 'Home', '/pages/home', NULL, NULL, 0, TRUE, TRUE, 'Página inicial do sistema'),
(2, 'Cadastros', '#', NULL, NULL, 1, TRUE, FALSE, 'Menu de cadastros gerais'),
(3, 'Relatórios', '#', NULL, NULL, 2, TRUE, FALSE, 'Menu de relatórios');