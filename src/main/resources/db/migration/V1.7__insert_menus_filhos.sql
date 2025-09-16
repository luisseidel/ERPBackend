-- V1.7: inserir menus filhos usando IDs fixos dos pais
INSERT INTO public.menu
(id, name,      url, parent_id,                 permission_id, order_position, active, home_page, description)
VALUES
(4, 'Menus',    '/pages/menus/consulta', 2,     1, 1, TRUE, FALSE, 'Gerenciamento de menus do sistema'),
(5, 'Usuários', '/pages/usuarios/consulta', 2,  2, 2, TRUE, FALSE, 'Gerenciamento de Usuários'),
(6, 'Roles',    '/pages/roles/consulta', 2,     3, 3, TRUE, FALSE, 'Gerenciamento de Roles de Usuários'),
(7, 'Cidades',  '/pages/cidades/consulta', 2,   4, 4, TRUE, FALSE, 'Gerenciamento de Cidades'),
(8, 'Estados',  '/pages/estados/consulta', 2,   5, 5, TRUE, FALSE, 'Gerenciamento de Estados'),
(9, 'Endereços', '/pages/enderecos/consulta', 2, 6, 6, TRUE, FALSE, 'Gerenciamento de Endereços');
-- Ajuste a sequência para não gerar conflito
SELECT setval('seq_menu', 9);