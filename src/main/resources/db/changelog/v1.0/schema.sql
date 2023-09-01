create schema if not exists wn45;
GO

create table if not exists wn45.stations (
     id serial primary key,
     name text not null unique
);
GO

create table if not exists wn45.station_codes (
      id serial primary key,
      number text not null unique
);
GO

create table if not exists wn45.stations_paths (
   id serial primary key,
   stations_id integer not null
        constraint fk_stations_paths_stations
        references wn45.stations(id),
   station_codes_id integer not null
        constraint fk_stations_paths_station_codes
        references wn45.station_codes(id),
   path integer not null
);
GO

create table if not exists wn45.notifications (
    id serial primary key,
    stations_paths_id integer not null
        constraint fk_notifications_stations_paths
        references wn45.stations_paths(id),

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
    density_brake_network_value integer not null,

    time_locomotive_trailer time,
    time_charge_network time,
    time_check_integrity time not null,
    time_finish time not null,
    surname_tail_employee text,
    surname_head_employee text not null,
    surname_machinist text not null,

    time_create timestamp not null
);
GO

create table if not exists wn45.others_parameters (
      id serial primary key,
      name text not null unique
);
GO

create table if not exists wn45.notifications_others_parameters (
      id serial primary key,
      notification_id integer not null
        constraint fk_notifications_notifications_others_parameters
        references wn45.notifications(id),
      others_parameters_id integer not null
        constraint fk_notifications_others_parameters_others_parameters
        references wn45.others_parameters(id)
);
GO