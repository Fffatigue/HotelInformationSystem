CREATE TABLE building
(
  building_id SERIAL NOT NULL,
  name VARCHAR(128) NOT NULL,
  PRIMARY KEY (building_id)
);

CREATE TABLE floor
(
  floor_num INT NOT NULL,
  building_id INT NOT NULL,
  PRIMARY KEY (floor_num, building_id),
  FOREIGN KEY (building_id) REFERENCES building(building_id) ON DELETE CASCADE
);

CREATE TABLE client
(
  client_id SERIAL NOT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE entity
(
  name VARCHAR(128) NOT NULL,
  discount INT NOT NULL,
  client_id INT NOT NULL,
  PRIMARY KEY (client_id),
  FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE CASCADE,
  UNIQUE (name)
);

CREATE TABLE individual
(
  full_name VARCHAR(128) NOT NULL,
  client_id INT NOT NULL,
  PRIMARY KEY (client_id),
  FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE CASCADE
);

CREATE TABLE service
(
  service_id SERIAL NOT NULL,
  name VARCHAR(128) NOT NULL,
  price INT NOT NULL,
  PRIMARY KEY (service_id),
  UNIQUE (name)
);

CREATE TABLE building_service
(
  building_id INT NOT NULL,
  service_id INT NOT NULL,
  PRIMARY KEY (building_id, service_id),
  FOREIGN KEY (building_id) REFERENCES building(building_id) ON DELETE CASCADE,
  FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE
);

CREATE TABLE room
(
  room_num INT NOT NULL,
  price INT NOT NULL,
  capacity INT NOT NULL,
  building_id INT NOT NULL,
  floor_num INT NOT NULL,
  PRIMARY KEY (room_num, building_id, floor_num),
  FOREIGN KEY (building_id, floor_num) REFERENCES floor(building_id, floor_num) ON DELETE CASCADE
);

CREATE TABLE reservation
(
  reservation_id SERIAL NOT NULL,
  arrival_date DATE NOT NULL,
  departure_date DATE NOT NULL,
  client_id INT NOT NULL,
  room_num INT NOT NULL,
  building_id INT NOT NULL,
  floor_num INT NOT NULL,
  PRIMARY KEY (reservation_id),
  FOREIGN KEY (client_id) REFERENCES client(client_id),
  FOREIGN KEY (room_num, building_id, floor_num) REFERENCES room(room_num, building_id, floor_num) ON DELETE CASCADE
);

CREATE TABLE used_service
(
  count INT NOT NULL,
  reservation_id INT NOT NULL,
  service_id INT NOT NULL,
  PRIMARY KEY (reservation_id, service_id),
  FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id) ON DELETE CASCADE,
  FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE
);

CREATE TABLE review
(
  review_id SERIAL NOT NULL,
  score INT NOT NULL,
  comment VARCHAR(256) NOT NULL,
  reservation_id INT NOT NULL,
  PRIMARY KEY (review_id),
  FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id) ON DELETE CASCADE

);