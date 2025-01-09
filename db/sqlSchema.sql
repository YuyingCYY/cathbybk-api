CREATE DATABASE fund_db;

CREATE TABLE Product(
    product_id VARCHAR(50) PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_short_name VARCHAR(50),
    id_grouped BOOLEAN
);

CREATE TABLE Price(
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(50) REFERENCES Product(product_id),
    date DATE NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);