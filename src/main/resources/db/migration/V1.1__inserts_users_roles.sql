INSERT INTO public.role(id, name)
VALUES (nextval('seq_role'), 'Administrador');
INSERT INTO public.role(id, name)
VALUES (nextval('seq_role'), 'Usuário');

INSERT INTO public.user(id, email, name, password)
VALUES (nextval('seq_user'), 'luis.seidel@example.com', 'Luis Seidel',
        '$2a$10$lT9BaejoLYWg2Fz9QALdke4cDtggYVgjSdTwbwRtR5WUrVeakG20a');

INSERT INTO public.user_role(id, user_id, role_id)
VALUES (nextval('seq_user_role'), 1, 1);