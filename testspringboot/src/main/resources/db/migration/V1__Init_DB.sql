CREATE TABLE public.tovar (
	id serial NOT NULL,
	id_category integer NOT NULL,
	name varchar(100) NOT NULL,
	cost integer NOT NULL,
	quantity_in_stock integer NOT NULL,
	description varchar(100) NOT NULL,
	photo bigint NULL,
	CONSTRAINT tovar_pk PRIMARY KEY (id)
);

CREATE TABLE public.booked (
	id serial NOT NULL,
	id_tovar integer NOT NULL,
	booked_quantity integer NOT NULL,
	id_user integer NOT NULL,
	CONSTRAINT booked_pk PRIMARY KEY (id)
);


CREATE TABLE public.category (
	id serial NOT NULL,
	name varchar(100) UNIQUE NOT NULL,
	description varchar(100) NOT NULL,
	CONSTRAINT category_pk PRIMARY KEY (id)
);



CREATE TABLE public.users (
	id serial NOT NULL,
	id_telegram bigint NOT NULL UNIQUE,
	name varchar(100) NULL,
	firstname varchar(100) NOT NULL,
	lastname varchar(100) NULL,
	phone varchar(11) NULL,
	mail varchar(100) NULL,
	agreement boolean NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);



CREATE TABLE public.carts (
	id serial NOT NULL,
	id_user integer NOT NULL,
	CONSTRAINT carts_pk PRIMARY KEY (id)
);

CREATE TABLE public.trash (
	id serial NOT NULL,
	id_tovar integer NOT NULL,
	quantity integer NOT NULL,
	id_cart integer NOT NULL,
	CONSTRAINT trash_pk PRIMARY KEY (id)
);



CREATE TABLE public.remind (
	id serial NOT NULL,
	id_user integer NOT NULL,
	id_tovar integer NOT NULL,
	is_delivered boolean NOT NULL,
	quantity integer NOT NULL,
	CONSTRAINT remind_pk PRIMARY KEY (id)
);

CREATE SEQUENCE users_seq START 1 OWNED BY users.id;
CREATE SEQUENCE carts_seq START 1 OWNED BY carts.id;
CREATE SEQUENCE category_seq START 4 OWNED BY category.id;
CREATE SEQUENCE remind_seq START 1 OWNED BY remind.id;
CREATE SEQUENCE tovar_seq START 10 OWNED BY tovar.id;
CREATE SEQUENCE trash_seq START 1 OWNED BY trash.id;
CREATE SEQUENCE booked_seq START 1 OWNED BY booked.id;


ALTER TABLE tovar ADD CONSTRAINT tovar_fk0 FOREIGN KEY (id_category) REFERENCES category(id);
ALTER TABLE carts ADD CONSTRAINT carts_fk0 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE trash ADD CONSTRAINT carts_fk0 FOREIGN KEY (id_cart) REFERENCES carts(id);
ALTER TABLE trash ADD CONSTRAINT trash_fk1 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE remind ADD CONSTRAINT remind_fk0 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE remind ADD CONSTRAINT remind_fk1 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE booked ADD CONSTRAINT booked_fk0 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE booked ADD CONSTRAINT booked_fk1 FOREIGN KEY (id_user) REFERENCES users(id);

INSERT INTO users (id, id_telegram, name, firstname, agreement) VALUES (0, 0, 'Admin', 'Admin', false);

INSERT INTO category (id, name, description) VALUES (1, 'Lego', 'Lorem Ipsum');
INSERT INTO category (id, name, description) VALUES (2, 'Barbie', 'Lorem Ipsum');
INSERT INTO category (id, name, description) VALUES (3, 'Mashiny', 'Lorem Ipsum');

INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (1, 1, 'Конструктор LEGO DUPLO Town Грузовой поезд', 11999, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (2, 1, 'Конструктор LEGO Minecraft Шахта крипера', 8989, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (3, 1, 'Конструктор LEGO Super Mario Приключения вместе с Марио', 4169, 100, 'Lorem Ipsum');

INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (4, 2, 'Набор игровой Barbie Спа-салон', 2199, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (5, 2, 'Набор игровой Barbie Йога', 2499, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (6, 2, 'Набор игровой Barbie для маникюра и педикюра', 2199, 100, 'Lorem Ipsum');

INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (7, 3, 'Машина Mobicaro 1:16 пожарная инерционная', 699, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (8, 3, 'Модель сборная Mobicaro Экскаватор с шуруповертом-двигателем', 889, 100, 'Lorem Ipsum');
INSERT INTO tovar (id, id_category , name, cost, quantity_in_stock, description)
VALUES (9, 3, 'Машина Mobicaro 1:16 Мусоровоз инерционная', 649, 100, 'Lorem Ipsum');



