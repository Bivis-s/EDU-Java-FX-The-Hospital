drop table accounts;

create table accounts
(
    id       integer primary key autoincrement not null,
    name     text                              not null,
    phone    text                              not null,
    password text                              not null,
    type     text
);

insert into accounts (name, phone, password, type)
values ('pupkin', '+3352677120', 'Asdf*&3gs;d', 'patient');

select *
from accounts;

select count(*)
from accounts;

create table tickets
(
    id         integer primary key autoincrement not null,
    date       text                              not null,
    time       text                              not null,
    patient_id integer                           not null,
    doctor_id  integer                           not null,
    foreign key (patient_id) references accounts (id),
    foreign key (doctor_id) references accounts (id)
);

insert into tickets (date, time, patient_id, doctor_id)
VALUES ('19.03.2021', '13:30', 1, 2);
insert into tickets (date, time, patient_id, doctor_id)
VALUES ('20.03.2021', '11:00', 4, 2);

select *
from tickets;

select count(*)
from tickets
where doctor_id = 2
  and date = '19.03.2021'
  and time = '13:30';

select date, time, doctor.id as doctor_id, patient.name as patient_name, patient.id as patient_id
from tickets
         inner join accounts doctor on doctor.id = tickets.doctor_id
         inner join accounts patient on patient.id = tickets.patient_id;