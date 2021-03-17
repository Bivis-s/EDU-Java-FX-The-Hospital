drop table accounts;

create table accounts
(
    id       integer primary key autoincrement,
    name     text,
    phone    text,
    password text,
    type     text
);

insert into accounts (name, phone, password, type)
values (
           'pupkin', '+3352677120', 'Asdf*&3gs;d', 'patient'
       );

select * from accounts;

select count(*)
from accounts;