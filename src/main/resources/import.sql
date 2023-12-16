-- INSERT za tabelu Company
INSERT INTO Company (company_id, company_name, address, description, rating)
VALUES
    (-1, 'Company1', 'Alekse Santica','Description1', 4.5),
    (-2, 'Company2', 'Lasla Gala','Description2', 3.8),
    (-3, 'Company3', 'Mite Ruzica','Description3', 4.2);

-- INSERT za tabelu Address
-- INSERT INTO Address (id, country, city, street, street_number, longitude, latitude, company_id)
-- VALUES
--     (-1, 'Country1', 'City1', 'Street1', 123, 45.678, 34.567, -1),
--     (-2, 'Country2', 'City2', 'Street2', 456, 12.345, 67.890, -2),
--     (-3, 'Country3', 'City3', 'Street3', 789, 78.901, 23.456, -3);

-- INSERT za tabelu Appointment
INSERT INTO Appointment (id, admin_name, admin_lastname, appointment_date_time, duration, status, company_id, is_reserved, user_id)
VALUES
    (-1, 'Admin1', 'AdminLastname1', '2023-12-21 10:00:00', 1.5, 0, -1, TRUE, -1),
    (-2, 'Admin2', 'AdminLastname2', '2023-12-22 14:30:00', 2.0, 1, -1, FALSE, NULL),
    (-3, 'Admin3', 'AdminLastname3', '2023-01-03 16:45:00', 1.8, 1, -3, FALSE, NULL);

-- INSERT za tabelu Equipment
-- INSERT za tabelu Equipment
INSERT INTO public.Equipment (id, name, equipment_type, description, rating, price, company_id)
VALUES
    (-1, 'Equipment1', 0, 'Description1', 4.2, 150.0, -1, 'null'),  -- 0 odgovara ordinalnoj vrednosti za TYPE1
    (-2, 'Equipment2', 1, 'Description2', 3.9, 120.0, -2, 'null'),  -- 1 odgovara ordinalnoj vrednosti za TYPE2
    (-3, 'Equipment3', 2, 'Description3', 4.5, 180.0, -1, 'null'),  -- 2 odgovara ordinalnoj vrednosti za TYPE3
    (-4, 'Equipment4', 3, 'Description4', 4.5, 180.0, -1, 'null'),  --3
    (-5, 'Equipment5', 4, 'Description5', 4.5, 180.0, -1, 'null');  --4



-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123
-- INSERT za tabelu User (primer za listu korisnika vezanih za kompaniju)
INSERT INTO Userr (id, address, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, username, company_id)
VALUES
    (-1, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', '2017-10-01 21:58:58.508-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic', -1),
    (-2, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', '2017-10-01 21:58:58.508-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic1', -1),
    (-3, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', '2017-10-01 21:58:58.508-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic2', -1);

INSERT INTO Role (id, name)
VALUES
    (-1, 'ROLE_USER'),
    (-2, 'ROLE_COMPANY_ADMIN'),
    (-3, 'ROLE_SYSTEM_ADMIN');

-- INSERT INTO Role (id, name) VALUES (-1, 'ROLE_USER');
-- INSERT INTO Role (id, name) VALUES (-2, 'ROLE_COMPANY_ADMIN');
-- INSERT INTO Role (id, name) VALUES (-3, 'ROLE_SYSTEM_ADMIN');

INSERT INTO user_role (user_id, role_id)
VALUES
    (-1, -1),
    (-2, -2),
    (-3, -3);

-- INSERT INTO user_role (user_id, role_id) VALUES (-1, -1); -- registrovanom korisniku dodeljujemo rolu USER
-- INSERT INTO user_role (user_id, role_id) VALUES (-2, -2); -- admin-u kompanije dodeljujemo rolu COMPANY_ADMIN
-- INSERT INTO user_role (user_id, role_id) VALUES (-3, -3); -- admin-u sistema dodeljujemo rolu SYSTEM_ADMIN

