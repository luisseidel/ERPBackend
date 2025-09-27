
insert into public.permission(id, name, description) values (
    nextval('seq_permission'), 'TASK', 'Permission related to Task Manager'
);

insert into public.role_permission(id, role_id, permission_id, consultar, adicionar, editar, excluir
) values (
    nextval('seq_role_permission'), 1, 9, true, true, true, true
);  -- TASK

INSERT INTO public.menu (id, name, url, parent_id, permission_id, order_position, active, home_page, icon, description
) VALUES (
    nextval('seq_menu'), 'Task Manager', '/pages/taskmanager/consulta', 2, 9, 8, TRUE, FALSE, NULL, 'Management of Tasks'
);

--Task Type
create table if not exists public.task_type(
    id serial primary key not null,
    name varchar(255) not null
);
create sequence if not exists public.seq_task_type increment 1 start 1 owned by task_type.id;

--TASKS
create table if not exists public.task(
    id serial primary key not null,
    name varchar(255) not null,
    description varchar(255) null,
    cron_expression varchar(20) not null,
    task_type integer not null references task_type(id),
    active boolean not null default false
);
create sequence if not exists public.seq_task increment 1 start 1 owned by task.id;

