drop table if exists  centro, vacinado, agendamento, stock_diario, stock_nacional cascade;

create table centro (
	nome varchar(128) unique,
	id serial unique,
	capacidade_diaria integer,
	primary key(id)
);

create table vacinado(
	id serial unique,
	id_centro integer references centro(id),
	codigo varchar(130) unique,
	cc varchar(128) unique,
	nome varchar(128),
	idade int,
	email varchar(128),
	data_vacina date default current_date,
	primary key (id)
);

create table agendamento(
	id serial unique,
	id_centro integer references centro(id),
	codigo varchar(130)  unique,
	cc varchar(128) unique,
	nome varchar(128),
	idade int,
	email varchar(128),
    confirmado integer, 
	primary key(cc)
);


create table stock_diario(
	id serial unique,
	id_centro integer references centro(id),
    n_vacinas integer,
	primary key(id)
);


create table stock_nacional(
	id serial unique,
	data date default current_date, 
    n_vacinas integer,
	primary key(id)
);


