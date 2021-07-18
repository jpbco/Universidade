drop table if exists  public.centro, public.stock_nacional, public.agendamento, public.stock_diario cascade;
-- public.centro definition

-- Drop table

-- DROP TABLE public.centro;

CREATE TABLE public.centro (
	id int8 NOT NULL,
	capacidade_diaria int4 NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT centro_pkey PRIMARY KEY (id)
);


-- public.stock_nacional definition

-- Drop table

-- DROP TABLE public.stock_nacional;

CREATE TABLE public.stock_nacional (
	id int8 NOT NULL,
	"data" date NULL,
	vacinas int4 NOT NULL,
	CONSTRAINT stock_nacional_pkey PRIMARY KEY (id)
);


-- public.agendamento definition

-- Drop table

-- DROP TABLE public.agendamento;

CREATE TABLE public.agendamento (
	id int8 NOT NULL,
	cc varchar(255) NULL,
	centro_name varchar(255) NULL,
	"data" date NULL,
	data_vacinacao date NULL,
	email varchar(255) NULL,
	idade int4 NOT NULL,
	"name" varchar(255) NULL,
	vacinado bool NOT NULL,
	centroid int8 NULL,
	CONSTRAINT agendamento_pkey PRIMARY KEY (id),
	CONSTRAINT fk5dv0twk2jx0hjhre6ngks0bbx FOREIGN KEY (centroid) REFERENCES public.centro(id)
);


-- public.stock_diario definition

-- Drop table

-- DROP TABLE public.stock_diario;

CREATE TABLE public.stock_diario (
	id int8 NOT NULL,
	centro_name varchar(255) NULL,
	"data" date NULL,
	vacinas int4 NOT NULL,
	centroid int8 NULL,
	CONSTRAINT stock_diario_pkey PRIMARY KEY (id),
	CONSTRAINT fkmkivrl7y77ajatsljaweigb52 FOREIGN KEY (centroid) REFERENCES public.centro(id)
);
