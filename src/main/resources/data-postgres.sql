-- INSERT za tabelu Company
INSERT INTO Company (company_id, company_name, description, rating)
VALUES
    (-1, 'Company1', 'Description1', 4.5),
    (-2, 'Company2', 'Description2', 3.8),
    (-3, 'Company3', 'Description3', 4.2);

-- INSERT za tabelu Address
INSERT INTO Address (id, country, city, street, street_number, longitude, latitude, company_id)
VALUES
    (1, 'Country1', 'City1', 'Street1', 123, 45.678, 34.567, -1),
    (2, 'Country2', 'City2', 'Street2', 456, 12.345, 67.890, -2),
    (3, 'Country3', 'City3', 'Street3', 789, 78.901, 23.456, -3);

-- INSERT za tabelu Appointment
INSERT INTO Appointment (id, admin_name, admin_lastname, appointment_date_time, duration, status, company_id)
VALUES
    (1, 'Admin1', 'AdminLastname1', '2023-01-01 10:00:00', 1.5, 0, -1),
    (2, 'Admin2', 'AdminLastname2', '2023-01-02 14:30:00', 2.0, 1, -2),
    (3, 'Admin3', 'AdminLastname3', '2023-01-03 16:45:00', 1.8, 1, -3);

-- INSERT za tabelu Equipment
-- INSERT za tabelu Equipment
INSERT INTO public.Equipment (id, name, equipment_type, description, rating, price, company_id)
VALUES
    (1, 'Equipment1', 0, 'Description1', 4.2, 150.0, -1),  -- 0 odgovara ordinalnoj vrednosti za TYPE1
    (2, 'Equipment2', 1, 'Description2', 3.9, 120.0, -2),  -- 1 odgovara ordinalnoj vrednosti za TYPE2
    (3, 'Equipment3', 2, 'Description3', 4.5, 180.0, -3);  -- 2 odgovara ordinalnoj vrednosti za TYPE3


-- INSERT za tabelu User (primer za listu korisnika vezanih za kompaniju)
INSERT INTO Userr (id, email, password, first_name, last_name, address, phone_number, profession, type, company_id)
VALUES
    (1, 'peraperic@gmail.com', 'pera123', 'Pera', 'Peric', 'Melhiora Erdujheljija 2', '+38763245234', 'programmer', 0, -1),
    (2, 'peraperic@gmail.com', 'pera123', 'Pera', 'Peric', 'Melhiora Erdujheljija 2', '+38763245234', 'programmer', 0, -2),
    (3, 'peraperic@gmail.com', 'pera123', 'Pera', 'Peric', 'Melhiora Erdujheljija 2', '+38763245234', 'programmer', 0, -3);

