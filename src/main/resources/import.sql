INSERT INTO pizzas ( description, name, photo, price) VALUES( 'Pomodoro e mozzarella', 'Margherita', 'https://media.istockphoto.com/id/1280329631/it/foto/pizza-margherita-italiana-con-pomodori-e-mozzarella-su-tagliere-in-legno-primo-tempo.jpg?s=612x612&w=0&k=20&c=zHj-LnJT-S72eemlWmLft5wYM96bXXn4JiXNcRHU28g=', 7.99);
INSERT INTO pizzas ( description, name, photo, price)  VALUES ('Con prosciutto, funghi, olive e carciofi', 'Capricciosa',"https://primochef.it/wp-content/uploads/2018/05/SH_pizza_capricciosa.jpg", 10.99);
INSERT INTO pizzas  ( description, name, photo, price) VALUES ('Con gorgonzola, mozzarella, pecorino e parmigiano', 'Quattro Formaggi',"https://media-assets.lacucinaitaliana.it/photos/61faed2abe60547b84221fab/4:3/w_1420,h_1065,c_limit/ricette-4-formaggi.jpg", 11.99);
INSERT INTO pizzas ( description, name, photo, price) VALUES ('Ricca di verdure come peperoni, pomodori e spinaci', 'Vegetariana',"https://www.fruttaweb.com/consigli/wp-content/uploads/2018/06/pizza-vegana.jpg", 9.99);
INSERT INTO ingredients(name) VALUES('melanzana');
INSERT INTO ingredients(name) VALUES('cipolla');
INSERT INTO ingredients(name) VALUES('gamberetto');
INSERT INTO ingredients(name) VALUES('ananas');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');


INSERT INTO users (email, first_name, last_name,  password) VALUES('john@email.com', 'John', 'Doe',  '{noop}john');
INSERT INTO users (email, first_name, last_name,  password) VALUES('jane@email.com', 'Jane', 'Smith', '{noop}jane');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);