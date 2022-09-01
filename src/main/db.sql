CREATE DATABASE IF NOT EXISTS bandfinder;
USE bandfinder;

CREATE TABLE IF NOT EXISTS users
(
    id INT AUTO_INCREMENT,
    email VARCHAR(255),
    password_hash VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255),
    stage_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bands
(
  id INT AUTO_INCREMENT,
  name VARCHAR(255),
  num_members INT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS band_users
(
  user_id INT,
  band_id INT,
  FOREIGN KEY (user_id) references users(id),
  FOREIGN KEY (band_id) references bands(id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    id INT,
    user_id INT,
    is_read BOOLEAN,
    message VARCHAR(65535),
    date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) references users(id)
);
