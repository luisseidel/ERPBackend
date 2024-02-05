INSERT INTO public.roles(id, name)
VALUES (nextval('seq_roles'), 'Administrador');
INSERT INTO public.roles(id, name)
VALUES (nextval('seq_roles'), 'Usuário');

INSERT INTO public.users(id, email, name, password)
VALUES (nextval('seq_users'), 'luis.seidel@example.com', 'Luis Seidel',
        '$2a$10$lT9BaejoLYWg2Fz9QALdke4cDtggYVgjSdTwbwRtR5WUrVeakG20a');
INSERT INTO public.user_roles(id, user_id, role_id)
VALUES (nextval('seq_user_roles'), 1, 1);