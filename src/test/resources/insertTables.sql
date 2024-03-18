INSERT INTO assortment_types(title)
VALUES ('desert'), ('drink');

INSERT INTO assortment(title, quantity, price, assortment_type_id)
VALUES ('drink1', 10, 12.3, 2),
       ('desert1', 13, 10.5, 1),
       ('desert2', 4, 11.2, 1),
       ('drink2', 5, 10.2, 2),
       ('desert3', 2, 8.3, 1);

INSERT INTO positions(title)
VALUES ('waiter'), ('confectioner'), ('barista');

INSERT INTO personal(first_name, last_name, patronymic, position_id)
VALUES ('FirstName1', 'LastName1', 'Patronymic1', 2),
       ('FirstName2', 'LastName2', 'Patronymic2', 1),
       ('FirstName3', 'LastName3', 'Patronymic3', 3),
       ('FirstName4', 'LastName4', 'Patronymic4', 1),
       ('FirstName5', 'LastName5', 'Patronymic5', 2);

INSERT INTO personal_email_addresses(email_address, personal_id)
VALUES ('mail1@temp.com', 1),
       ('mail2@temp.ua', 3),
       ('mail3@temp.org', 3),
       ('mail4@temp.com', 2),
       ('mail5@temp.ua', 5),
       ('mail6@temp.org.ua', 4);

INSERT INTO personal_phone_numbers(phone_number, personal_id)
VALUES ('+380998877666', 2),
       ('+380998811666', 1),
       ('+380998687666', 2),
       ('+380924877666', 4),
       ('+380998877086', 5),
       ('+380998745666', 3);

INSERT INTO clients(first_name, last_name, patronymic, birth_date, contact_phone, contact_email, discount)
VALUES ('ClientFirstName1', 'ClientLastName1', 'ClientPatron1', '2000-01-02', '+380998877766', 'clientmail1@temp.com.org.ua', 12),
       ('ClientFirstName2', 'ClientLastName2', 'ClientPatron2', '2000-02-12', '+380901877766', 'clientmail2@temp.org.ua', 0),
       ('ClientFirstName3', 'ClientLastName3', 'ClientPatron3', '2000-03-22', '+380998341766', 'clientmail3@temp.com.ua', 5),
       ('ClientFirstName4', 'ClientLastName4', 'ClientPatron4', '2000-04-15', '+380998107766', 'clientmail4@temp.ua', 7);
