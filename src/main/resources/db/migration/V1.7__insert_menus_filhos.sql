-- V1.7: inserir menus filhos usando IDs fixos dos pais
INSERT INTO public.menu
(id, name,      url, parent_id,                 permission_id, order_position, active, home_page, icon, description)
VALUES
(nextval('seq_menu'), 'Menus',    '/pages/menus/consulta', 2,     1, 1, TRUE, FALSE, NULL, 'Gerenciamento de menus do sistema'),
(nextval('seq_menu'), 'Usuários', '/pages/usuarios/consulta', 2,  2, 2, TRUE, FALSE, NULL, 'Gerenciamento de Usuários'),
(nextval('seq_menu'), 'Roles',    '/pages/roles/consulta', 2,     3, 3, TRUE, FALSE, NULL, 'Gerenciamento de Roles de Usuários'),
(nextval('seq_menu'), 'Cidades',  '/pages/cidades/consulta', 2,   4, 4, TRUE, FALSE, NULL, 'Gerenciamento de Cidades'),
(nextval('seq_menu'), 'Estados',  '/pages/estados/consulta', 2,   5, 5, TRUE, FALSE, NULL, 'Gerenciamento de Estados'),
(nextval('seq_menu'), 'Endereços', '/pages/enderecos/consulta', 2, 6, 6, TRUE, FALSE, NULL, 'Gerenciamento de Endereços'),
(nextval('seq_menu'), 'Permissões', '/pages/permissoes/consulta', 2, 7, 7, TRUE, FALSE, NULL, 'Gerenciamento de Permissões'),
(nextval('seq_menu'), 'Role Permission', '/pages/rolepermission/consulta', 2, 7, 7, TRUE, FALSE, NULL, 'Gerenciamento de Permissões x Consultas')
;
ALTER SEQUENCE seq_menu RESTART 12;