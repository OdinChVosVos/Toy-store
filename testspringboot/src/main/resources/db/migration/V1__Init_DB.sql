CREATE TABLE public.tovar (
	id serial NOT NULL,
	id_category integer NOT NULL,
	name varchar(100) NOT NULL,
	cost integer NOT NULL CHECK(cost > 0),
	quantity_in_stock integer NOT NULL CHECK(quantity_in_stock >= 0),
	description varchar(100) NOT NULL,
	photo bigint NULL,
	CONSTRAINT tovar_pk PRIMARY KEY (id)
);

CREATE TABLE public.booked (
	id serial NOT NULL,
	id_tovar integer NOT NULL,
	booked_quantity integer NOT NULL CHECK(booked_quantity > 0),
	id_user integer NOT NULL,
	CONSTRAINT booked_pk PRIMARY KEY (id)
);


CREATE TABLE public.category (
	id serial NOT NULL,
	name varchar(100) UNIQUE NOT NULL,
	description varchar(100) NOT NULL,
	CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE public.journal (
	id serial NOT NULL,
	user_id integer NOT NULL,
	datein timestamp NOT NULL,
	description varchar(100) NULL,
	CONSTRAINT journal_pk PRIMARY KEY (id)
);

CREATE TABLE public.users (
	id serial NOT NULL,
	id_telegram bigint NOT NULL UNIQUE,
	name varchar(100) NOT NULL UNIQUE,
	password varchar(100) NOT NULL,
	active boolean NOT NULL,
	firstname varchar(100) NOT NULL,
	lastname varchar(100) NULL,
	phone varchar(15) NULL,
	mail varchar(100) NULL,
	agreement boolean NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE public.roles (
	id serial NOT NULL,
	name varchar(100) NOT NULL UNIQUE,
	description varchar(100) NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);

CREATE TABLE public.users_roles (
	id serial NOT NULL,
	user_id integer NOT NULL,
	role_id integer NOT NULL,
	UNIQUE (user_id, role_id),
	CONSTRAINT users_roles_pk PRIMARY KEY (id)
);

CREATE TABLE public.carts (
	id serial NOT NULL,
	id_user integer NOT NULL,
	description varchar(100) NULL,
	CONSTRAINT carts_pk PRIMARY KEY (id)
);

CREATE TABLE public.trash (
	id serial NOT NULL,
	id_tovar integer NOT NULL,
	quantity integer NOT NULL CHECK(quantity > 0),
	id_cart integer NOT NULL,
	CONSTRAINT trash_pk PRIMARY KEY (id)
);

CREATE TABLE public.remind (
	id serial NOT NULL,
	id_user integer NOT NULL,
	id_tovar integer NOT NULL,
	is_delivered boolean NOT NULL,
	quantity integer NOT NULL CHECK(quantity > 0),
	CONSTRAINT remind_pk PRIMARY KEY (id)
);

CREATE TABLE public.archive_carts (
	id serial NOT NULL,
	id_user integer NOT NULL,
	description varchar(100) NULL,
	CONSTRAINT archive_carts_pk PRIMARY KEY (id)
);

CREATE TABLE public.archive_trash (
	id serial NOT NULL,
	id_tovar integer NOT NULL,
	quantity integer NOT NULL CHECK(quantity > 0),
	id_cart integer NOT NULL,
	CONSTRAINT archive_trash_pk PRIMARY KEY (id)
);

CREATE SEQUENCE users_seq START 1 OWNED BY users.id;
CREATE SEQUENCE carts_seq START 1 OWNED BY carts.id;
CREATE SEQUENCE category_seq START 4 OWNED BY category.id;
CREATE SEQUENCE remind_seq START 1 OWNED BY remind.id;
CREATE SEQUENCE tovar_seq START 10 OWNED BY tovar.id;
CREATE SEQUENCE trash_seq START 1 OWNED BY trash.id;
CREATE SEQUENCE booked_seq START 1 OWNED BY booked.id;
CREATE SEQUENCE roles_seq START 3 OWNED BY roles.id;
CREATE SEQUENCE users_roles_seq START 2 OWNED BY users_roles.id;
CREATE SEQUENCE journal_seq START 1 OWNED BY journal.id;
CREATE SEQUENCE archive_trash_seq START 1 OWNED BY archive_trash.id;
CREATE SEQUENCE archive_carts_seq START 1 OWNED BY archive_carts.id;

ALTER TABLE tovar ADD CONSTRAINT tovar_fk0 FOREIGN KEY (id_category) REFERENCES category(id);
ALTER TABLE carts ADD CONSTRAINT carts_fk0 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE trash ADD CONSTRAINT trash_fk0 FOREIGN KEY (id_cart) REFERENCES carts(id);
ALTER TABLE trash ADD CONSTRAINT trash_fk1 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE remind ADD CONSTRAINT remind_fk0 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE remind ADD CONSTRAINT remind_fk1 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE booked ADD CONSTRAINT booked_fk0 FOREIGN KEY (id_tovar) REFERENCES tovar(id);
ALTER TABLE booked ADD CONSTRAINT booked_fk1 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE users_roles ADD CONSTRAINT users_roles_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE users_roles ADD CONSTRAINT users_roles_fk1 FOREIGN KEY (role_id) REFERENCES roles(id);
ALTER TABLE journal ADD CONSTRAINT journal_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE archive_carts ADD CONSTRAINT archive_carts_fk0 FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE archive_trash ADD CONSTRAINT archive_trash_fk0 FOREIGN KEY (id_cart) REFERENCES archive_carts(id);
ALTER TABLE archive_trash ADD CONSTRAINT archive_trash_fk1 FOREIGN KEY (id_tovar) REFERENCES tovar(id);

CREATE OR REPLACE FUNCTION get_cart_by_user(var_id integer) RETURNS integer AS $$
	DECLARE res integer;
    BEGIN
        SELECT id
       		INTO res FROM carts WHERE id_user = var_id LIMIT 1;
       	RETURN res;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE archive_procedure(var_id integer)
LANGUAGE SQL
AS $$
  	INSERT INTO archive_carts
	SELECT *
	FROM carts
	WHERE carts.id_user = var_id;

	INSERT INTO archive_trash
	SELECT *
	FROM trash
	WHERE trash.id_cart = get_cart_by_user(var_id);

	DELETE FROM trash WHERE trash.id_cart = get_cart_by_user(var_id);
$$;

CREATE OR REPLACE PROCEDURE delete_user_procedure(var_id integer)
LANGUAGE SQL
AS $$
	DELETE FROM booked WHERE booked.id_user = var_id;
	DELETE FROM remind WHERE remind.id_user = var_id;
	DELETE FROM journal WHERE journal.user_id = var_id;
	DELETE FROM users_roles WHERE users_roles.user_id = var_id;
	DELETE FROM carts WHERE carts.id_user = var_id;
	DELETE FROM archive_carts WHERE archive_carts.id_user = var_id;
$$;

CREATE OR REPLACE PROCEDURE delete_archive_carts_procedure(var_id integer)
LANGUAGE SQL
AS $$
	DELETE FROM archive_trash WHERE archive_trash.id_cart = var_id;
$$;


CREATE OR REPLACE FUNCTION users() RETURNS trigger AS $$
    BEGIN
        IF NOT NEW.mail IS NULL AND NOT NEW.mail ~ '^[A-z0-9]*@[a-z]*\.[a-z]{1,4}$' THEN
            RAISE EXCEPTION 'email must be valid';
        END IF;
		IF NEW."active" IS NULL THEN
            NEW."active" = true;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION role() RETURNS trigger AS $$
    BEGIN
        IF NOT NEW."name" IS NULL AND NOT NEW."name" ~ '^ROLE_[A-Z]*$' THEN
            IF NOT NEW."name" ~ '[A-Z]*$' THEN
                RAISE EXCEPTION 'role must be valid';
            ELSE
                NEW."name" = 'ROLE_' || NEW."name";
            END IF;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION journal() RETURNS trigger AS $$
    BEGIN
        IF TG_OP != 'UPDATE' THEN
            NEW."datein" = CURRENT_TIMESTAMP;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION archive() RETURNS trigger AS $$
    BEGIN
        CALL archive_procedure(OLD.id_user);
        RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

--CREATE OR REPLACE FUNCTION delete_cart() RETURNS trigger AS $$
--    BEGIN
--        CALL delete_cart_procedure(OLD.id);
--        RETURN OLD;
--    END;
--$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_archive_carts() RETURNS trigger AS $$
    BEGIN
        CALL delete_archive_carts_procedure(OLD.id);
        RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_user() RETURNS trigger AS $$
    BEGIN
        CALL delete_user_procedure(OLD.id);
        RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER delete_user BEFORE DELETE ON users
FOR EACH ROW
EXECUTE FUNCTION delete_user();

CREATE OR REPLACE TRIGGER delete_archive_carts BEFORE DELETE ON archive_carts
FOR EACH ROW
EXECUTE FUNCTION delete_archive_carts();

CREATE OR REPLACE TRIGGER archive BEFORE DELETE ON carts
FOR EACH ROW
EXECUTE FUNCTION archive();

--CREATE OR REPLACE TRIGGER delete_cart BEFORE DELETE ON carts
--FOR EACH ROW
--EXECUTE FUNCTION delete_cart();

CREATE OR REPLACE TRIGGER journal BEFORE INSERT OR UPDATE ON journal
FOR EACH ROW
EXECUTE FUNCTION journal();

CREATE OR REPLACE TRIGGER users BEFORE INSERT OR UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION users();

CREATE OR REPLACE TRIGGER role BEFORE INSERT OR UPDATE ON roles
FOR EACH ROW
EXECUTE FUNCTION role();

INSERT INTO roles (id, name, description) VALUES (1, 'USER', 'Lorem Ipsum');
INSERT INTO roles (id, name, description) VALUES (2, 'ADMIN', 'Lorem Ipsum');

INSERT INTO users (id, id_telegram, name, password, active, firstname, agreement) VALUES (0, 0, 'Admin', 'Admin', true, 'Admin', false);

INSERT INTO users_roles (id, user_id, role_id) VALUES (1, 0, 2);

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



