INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES('Guillermo');
INSERT INTO client(name) VALUES('Alejandro');
INSERT INTO client(name) VALUES('Sergio');
INSERT INTO client(name) VALUES('Alonso');
INSERT INTO client(name) VALUES('Javier');
INSERT INTO client(name) VALUES('Manuel');
INSERT INTO client(name) VALUES('Marcos');
INSERT INTO client(name) VALUES('Alberto');

INSERT INTO loan (game_id, client_id, start_date, end_date) 
VALUES (1, 1, '2020-01-01', '2020-01-06');
INSERT INTO loan (game_id, client_id, start_date, end_date) 
VALUES (2, 1, '2020-01-02', '2020-01-14');
INSERT INTO loan (game_id, client_id, start_date, end_date) 
VALUES (1, 1, '2020-01-07', '2020-01-14');
INSERT INTO loan (game_id, client_id, start_date, end_date) 
VALUES (3, 2, '2020-01-01', '2020-01-09');
INSERT INTO loan (game_id, client_id, start_date, end_date) 
VALUES (4, 3, '2020-01-01', '2020-01-10');

