drop table accounts;
drop table doctors;
drop table patients;
drop table appointments;
drop table diseases;
drop table medical_records;
drop table card_records;
drop table medical_cards;

create table accounts
(
    id       integer primary key autoincrement,
    phone    text,
    password text
);

create table doctors
(
    id         integer primary key autoincrement,
    name       text,
    type       text,
    account_id integer,
    foreign key (account_id) references accounts (id)
);

create table patients
(
    id            integer primary key autoincrement,
    name          text,
    date_of_birth text,
    address       text,
    account_id    integer,
    foreign key (account_id) references accounts (id)
);

create table appointments
(
    id         integer primary key autoincrement,
    date       text,
    time       text,
    patient_id integer,
    doctor_id  integer,
    foreign key (patient_id) references patients (id),
    foreign key (doctor_id) references doctors (id)
);

create table diseases
(
    id     integer primary key autoincrement,
    name   text,
    degree integer
);

create table medical_records
(
    id         integer primary key autoincrement,
    note       text,
    disease_id integer,
    foreign key (disease_id) references diseases (id)
);

create table card_records
(
    medical_card_id   integer,
    medical_record_id integer,
    foreign key (medical_card_id) references medical_cards (id),
    foreign key (medical_record_id) references medical_records (id)
);

create table medical_cards
(
    id         integer primary key autoincrement,
    patient_id integer,
    foreign key (patient_id) references patients (id)
);