INSERT INTO Address (id, country, city, street, street_number, latitude, longitude)
VALUES
    (-1, 'Srbija', 'Beograd', 'Knez Mihajlova', 23, 43.23, 33.23),
    (-2, 'Madjarska', 'Budimpesta', 'Vaci utca', 33, 23.12, 12.33),
    (-3, 'Austrija', 'Bec', 'Mariahilferstrasse', 456, 12.32, 65.32);

INSERT INTO Company (id, company_name, address_id, description, rating)
VALUES
    (-1, 'Company1', -3,'Description1', 4.5),
    (-2, 'Company2', -1,'Description2', 3.8),
    (-3, 'Company3', -2,'Description3', 4.2);

INSERT INTO Appointment (id, date_time, duration, status, company_id, is_reserved, user_id, admin_id)
VALUES
    (-1, '2023-12-21 10:00:00', 20, 0, -1, FALSE, NULL, -2),
    (-2, '2023-12-22 14:30:00', 15, 0, -1, FALSE, NULL, -2),
    (-3, '2023-12-28 16:15:00', 40, 0, -3, FALSE, NULL, -2),
    (-4, '2023-12-26 17:45:00', 40, 0, -2, FALSE, NULL, -2);

INSERT INTO Equipment (id, name, type, description, rating, price, company_id, appointment_id)
VALUES
    (-1, 'Equipment1', 0, 'Description1', 4.2, 150.0, -1, -1),  -- 0 odgovara ordinalnoj vrednosti za TYPE1
    (-2, 'Equipment2', 1, 'Description2', 3.9, 120.0, -2, NULL),  -- 1 odgovara ordinalnoj vrednosti za TYPE2
    (-3, 'Equipment3', 2, 'Description3', 4.5, 180.0, -1, -1),  -- 2 odgovara ordinalnoj vrednosti za TYPE3
    (-4, 'Equipment4', 3, 'Description4', 4.5, 180.0, -1, NULL),  --3
    (-5, 'Equipment5', 4, 'Description5', 4.5, 180.0, -1, NULL),  --4
    (-6, 'Equipment6', 4, 'Description6', 4.5, 180.0, -3, NULL);

INSERT INTO _user (id, address_id, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, username, company_id, role)
VALUES
    (-1, -1, 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic', -1, 'USER'),
    (-2, -2 , 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic1', -1, 'COMPANY_ADMIN'),
    (-3, -3 , 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic2', -1, 'SYSTEM_ADMIN');

