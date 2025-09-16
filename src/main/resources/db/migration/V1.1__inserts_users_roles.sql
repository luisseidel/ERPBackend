INSERT INTO public.role(id, name) VALUES (nextval('seq_role'), 'Administrador');
INSERT INTO public.role(id, name) VALUES (nextval('seq_role'), 'Usuário');
ALTER SEQUENCE public.seq_role RESTART 3;

INSERT INTO public.usuario(id, email, name, password)
VALUES (nextval('seq_usuario'), 'luis.seidel@example.com', 'Luis Seidel',
        '$2a$10$lT9BaejoLYWg2Fz9QALdke4cDtggYVgjSdTwbwRtR5WUrVeakG20a');

INSERT INTO public.usuario(id, email, name, password)
VALUES (nextval('seq_usuario'), 'asd@asd.com', 'Usuario Comum',
        '$2a$10$lT9BaejoLYWg2Fz9QALdke4cDtggYVgjSdTwbwRtR5WUrVeakG20a');

ALTER SEQUENCE public.seq_usuario RESTART 3;

INSERT INTO public.user_role(id, user_id, role_id) VALUES (nextval('seq_user_role'), 1, 1);
INSERT INTO public.user_role(id, user_id, role_id) VALUES (nextval('seq_user_role'), 2, 2);

ALTER SEQUENCE seq_user_role RESTART 3;

insert into public.permission(id, name, description) values
(nextval('seq_permission'), 'MENU', 'Permissões relacionadas a Menus'),
(nextval('seq_permission'), 'USER', 'Permissões relacionadas a Usuários'),
(nextval('seq_permission'), 'ROLE', 'Permissões relacionadas a Roles'),
(nextval('seq_permission'), 'PERMISSION', 'Permissões relacionadas a Permissões'),
(nextval('seq_permission'), 'ESTADO', 'Permissões relacionadas a Estados'),
(nextval('seq_permission'), 'CIDADE', 'Permissões relacionadas a Cidades'),
(nextval('seq_permission'), 'ENDERECO', 'Permissões relacionadas a Endereços');
ALTER SEQUENCE seq_permission RESTART 8;

-- ATRIBUINDO PERMISSIONS A ROLES (role_permission)
-- Admin recebe todas as permissões
insert into public.role_permission(id, role_id, permission_id, consultar, adicionar, editar, excluir) values
(nextval('seq_role_permission'), 1, 1, true, true, true, true),  -- MENU
(nextval('seq_role_permission'), 1, 2, true, true, true, true),  -- USUARIO
(nextval('seq_role_permission'), 1, 3, true, true, true, true),  -- ROLE
(nextval('seq_role_permission'), 1, 4, true, true, true, true),  -- PERMISSION
(nextval('seq_role_permission'), 1, 5, true, true, true, true),  -- ESTADO
(nextval('seq_role_permission'), 1, 6, true, true, true, true),  -- CIDADE
(nextval('seq_role_permission'), 1, 7, true, true, true, true);  -- ENDERECO

-- Usuário padrão (USER) com permissões apenas de consulta e edição em cidades e estados
insert into public.role_permission(id, role_id, permission_id, consultar, adicionar, editar, excluir) values
(nextval('seq_role_permission'), 2, 6, true, false, true, false),  -- CIDADE
(nextval('seq_role_permission'), 2, 7, true, false, true, false);  -- ESTADO

ALTER SEQUENCE seq_role_permission RESTART 10;