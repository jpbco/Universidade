drop table if exists vacinados, fila, centro, vacinas cascade;

create table centro (
	nome varchar(128) unique,
	id serial unique,
	primary key(id)
);

create table fila(
	id serial unique,
	id_centro integer references centro(id),
	codigo varchar(130)  unique,
	cc varchar(128) unique,
	nome varchar(128),
	idade int,
	genero varchar(128),
	primary key(cc)
);

create table vacinados(
	id serial unique,
	id_centro integer references centro(id),
	codigo varchar(130) unique,
	cc varchar(128) unique,
	nome varchar(128),
	idade int,
	genero varchar(128),
	tipo_vacina varchar(128),
	efeitos_secundarios bool default false,
	data_vacina timestamp default current_timestamp,
	primary key (id)
);

create table vacinas(
	id serial unique,
	nome varchar(128) unique,
	primary key(id)
);


insert into centro (nome)
values 
	('Escola'),
	('Universidade'),
	('Hospital'),
	('Campo de futebol'),
	('Burger King'),
	('Parque') ;

insert into vacinas (nome)
values 
	('A'),
	('B'),
	('C'),
	('Z');


