CREATE TABLE CONTACTS (id INT null Primary Key AUTO_INCREMENT, name VARCHAR(20), phone VARCHAR(25), address VARCHAR(100), email VARCHAR(25), role VARCHAR(20));


INSERT INTO CONTACTS (name, phone, address, email, role) VALUES
('Adelia', '647 478 9384', 'Maple St.', 'adelia@gmail.com', 'Administrator'),
('Keidon', '647 574 987', 'Snow St.', 'keidon@gmail.com', 'Member'),
('Martie', '647 567 5678', 'Main St.', 'martie@gmail.com', 'Guest');