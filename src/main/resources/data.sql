INSERT INTO Company (id, company_name, address, description, rating)
VALUES
    (-1, 'Company1', 'Alekse Santica','Description1', 4.5),
    (-2, 'Company2', 'Lasla Gala','Description2', 3.8),
    (-3, 'Company3', 'Mite Ruzica','Description3', 4.2);

INSERT INTO Appointment (id, admin_name, admin_lastname, date_time, duration, status, company_id, is_reserved, user_id)
VALUES
    (-1, 'Admin1', 'AdminLastname1', '2023-12-21 10:00:00', 20, 0, -1, FALSE, NULL),
    (-2, 'Admin2', 'AdminLastname2', '2023-12-22 14:30:00', 15, 0, -1, FALSE, NULL),
    (-3, 'Admin3', 'AdminLastname3', '2023-12-28 16:15:00', 40, 0, -3, FALSE, NULL),
    (-4, 'Admin4', 'AdminLastname4', '2023-12-26 17:45:00', 40, 0, -2, FALSE, NULL);

INSERT INTO Equipment (id, name, type, description, rating, price, company_id, appointment_id)
VALUES
    (-1, 'Equipment1', 0, 'Description1', 4.2, 150.0, -1, -1),  -- 0 odgovara ordinalnoj vrednosti za TYPE1
    (-2, 'Equipment2', 1, 'Description2', 3.9, 120.0, -2, -1),  -- 1 odgovara ordinalnoj vrednosti za TYPE2
    (-3, 'Equipment3', 2, 'Description3', 4.5, 180.0, -1, NULL),  -- 2 odgovara ordinalnoj vrednosti za TYPE3
    (-4, 'Equipment4', 3, 'Description4', 4.5, 180.0, -1, NULL),  --3
    (-5, 'Equipment5', 4, 'Description5', 4.5, 180.0, -1, NULL),  --4
    (-6, 'Equipment6', 4, 'Description6', 4.5, 180.0, -3, NULL);

INSERT INTO _user (id, address, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, profession, username, company_id, role)
VALUES
    (-1, 'Melhiora Erdujheljija 2', 'lukasavkov1@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic', NULL, 'USER'),
    (-2, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic1', -1, 'COMPANY_ADMIN'),
    (-3, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic2', NULL, 'SYSTEM_ADMIN'),
    (-4, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic3', -2, 'COMPANY_ADMIN'),
    (-5, 'Melhiora Erdujheljija 2', 'peraperic@gmail.com', true, 'Pera', 'Peric', null, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+38763245234', 'programmer', 'peraperic4', -3, 'COMPANY_ADMIN');