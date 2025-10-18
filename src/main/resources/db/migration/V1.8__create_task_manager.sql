
insert into public.permission(id, name, description) values (
    nextval('seq_permission'), 'TASK', 'Permission related to Task Manager'
);
ALTER SEQUENCE seq_permission RESTART 10;

insert into public.role_permission(id, role_id, permission_id, consultar, adicionar, editar, excluir
) values (
    nextval('seq_role_permission'), 1, 9, true, true, true, true
);  -- TASK
ALTER SEQUENCE seq_role_permission RESTART 12;

INSERT INTO public.menu (id, name, url, parent_id, permission_id, order_position, active, home_page, icon, description
) VALUES (
    nextval('seq_menu'), 'Task Manager', '/pages/taskmanager/consulta', 2, 9, 8, TRUE, FALSE, NULL, 'Management of Tasks'
);

ALTER SEQUENCE seq_menu RESTART 13;

--TASKS
create table if not exists public.task(
    id serial primary key not null,
    name varchar(255) not null,
    description varchar(255) null,
    cron_expression varchar(20) not null,
    task_type integer not null,
    active boolean not null default false,
    register_job boolean not null default false
);
create sequence if not exists public.seq_task increment 1 start 1 owned by task.id;

