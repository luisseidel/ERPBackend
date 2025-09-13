--MENUS
create table if not exists public.menu
(
    id   serial primary key,
    name varchar(255) not null,
    url  varchar(255) not null,
    parent_id bigint null references public.menu (id),
    permission_id bigint references permission(id),
    order_position integer default 0,
    active boolean default true,
    home_page boolean default false,
    icon varchar(50),
    description varchar(500)
);
create sequence if not exists public.seq_menu increment 1 start 1 owned by menu.id;

CREATE INDEX idx_menu_parent_id ON public.menu(parent_id);
CREATE INDEX idx_menu_active ON public.menu(active);
CREATE INDEX idx_menu_order ON public.menu(order_position);
CREATE INDEX idx_menu_home_page ON public.menu(home_page);
