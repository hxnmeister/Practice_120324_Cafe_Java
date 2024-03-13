CREATE TABLE assortment_types (
    id SERIAL PRIMARY KEY,
    title VARCHAR(20)
);

CREATE TABLE assortment (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    quantity INTEGER,
    price MONEY,
    assortment_type_id INTEGER,

    FOREIGN KEY (assortment_type_id) REFERENCES assortment_types(id)
);

CREATE TABLE positions (
    id SERIAL PRIMARY KEY,
    title VARCHAR(20)
);

CREATE TABLE personal (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    patronymic VARCHAR(50),
    position_id INTEGER,

    FOREIGN KEY (position_id) REFERENCES positions(id)
);

CREATE TABLE personal_phone_numbers (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20),
    personal_id INTEGER,

    FOREIGN KEY(personal_id) REFERENCES personal(id)
);

CREATE TABLE personal_email_addresses (
    id SERIAL PRIMARY KEY,
    email_address VARCHAR(100),
    personal_id INTEGER,

    FOREIGN KEY(personal_id) REFERENCES personal(id)
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    patronymic VARCHAR(50),
    birth_date DATE,
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    discount INTEGER
);

CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    work_date DATE,
    work_hours_begin TIME,
    work_hours_end TIME,
    personal_id INTEGER,

    FOREIGN KEY(personal_id) REFERENCES personal(id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    price MONEY,
    price_with_discount MONEY,
    timestamp TIMESTAMP,
    personal_id INTEGER,
    client_id INTEGER,

    FOREIGN KEY (personal_id) REFERENCES personal(id),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE orders_and_assortment(
    id SERIAL PRIMARY KEY,
    order_id INTEGER,
    assortment_id INTEGER,

    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (assortment_id) REFERENCES assortment(id)
);