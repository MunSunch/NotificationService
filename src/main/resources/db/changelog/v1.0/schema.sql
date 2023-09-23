create schema wn45;
GO

create table wn45.employees (
    id serial primary key,
    name text not null,
    surname text not null,
    patronymic text not null
);
GO

create table wn45.notifications (
    id serial primary key,
    station_code integer not null,
    path integer not null,

    locomotive_series text not null,
    locomotive_number integer not null,

    train_number integer not null,
    train_type text not null,
    train_weight integer not null,
    train_axes integer not null,
    train_units integer not null,
    train_number_tail varchar(8) not null,
    train_number_oncoming varchar(8),

    press_required integer not null,
    press_actual integer not null,
    union_min_press integer not null,
    hand_brakes_required integer not null,
    hand_brakes_actual integer not null,
    density_brake_network_second integer not null,
    density_brake_network_fourth integer,
    density_brake_network_value real not null,

    time_locomotive_trailer time,
    time_charge_network time,
    time_check_integrity time not null,
    datetime_finish timestamp not null,

    tail_employee_id integer not null
        constraint fk_notifications_head_employees
            references wn45.employees(id),
    head_employee_id integer not null
        constraint fk_notifications_tail_employees
            references wn45.employees(id),
    machinist_id integer not null
        constraint fk_notifications_machinist_employees
            references wn45.employees(id),

    time_create timestamp not null
);
GO

create table wn45.others_parameters (
    id serial primary key,
    name text not null unique
);
GO

create table wn45.notifications_others (
    id serial primary key,
    notification_id integer not null
       constraint fk_notifications_others_notifications
           references wn45.notifications(id),
    others_parameters_id integer not null
       constraint fk_notifications_others_others
           references wn45.others_parameters(id)
);