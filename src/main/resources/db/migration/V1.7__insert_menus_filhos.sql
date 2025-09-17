-- V1.7: inserir menus filhos usando IDs fixos dos pais
INSERT INTO public.menu
(id, name,      url, parent_id,                 permission_id, order_position, active, home_page, description)
VALUES
(nextval('seq_menu'), 'Menus',    '/pages/menus/consulta', 2,     1, 1, TRUE, FALSE, 'Gerenciamento de menus do sistema'),
(nextval('seq_menu'), 'Usuários', '/pages/usuarios/consulta', 2,  2, 2, TRUE, FALSE, 'Gerenciamento de Usuários'),
(nextval('seq_menu'), 'Roles',    '/pages/roles/consulta', 2,     3, 3, TRUE, FALSE, 'Gerenciamento de Roles de Usuários'),
(nextval('seq_menu'), 'Cidades',  '/pages/cidades/consulta', 2,   4, 4, TRUE, FALSE, 'Gerenciamento de Cidades'),
(nextval('seq_menu'), 'Estados',  '/pages/estados/consulta', 2,   5, 5, TRUE, FALSE, 'Gerenciamento de Estados'),
(nextval('seq_menu'), 'Endereços', '/pages/enderecos/consulta', 2, 6, 6, TRUE, FALSE, 'Gerenciamento de Endereços'),
(nextval('seq_menu'), 'Permissões', '/pages/permissoes/consulta', 2, 7, 7, TRUE, FALSE, 'Gerenciamento de Permissões')
;
-- Ajuste a sequência para não gerar conflito
ALTER SEQUENCE seq_menu RESTART 11;