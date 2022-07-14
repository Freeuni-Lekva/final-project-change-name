DROP DATABASE IF EXISTS bandfinder;
CREATE DATABASE bandfinder;
USE bandfinder;

CREATE TABLE users
(
    id INT AUTO_INCREMENT,
    email VARCHAR(254),
    password_hash VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255),
    stage_name VARCHAR(255),
    PRIMARY KEY (id)
);