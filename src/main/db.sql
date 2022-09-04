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
    PRIMARY KEY (id),
    FULLTEXT (first_name, surname, stage_name)
);

CREATE TABLE IF NOT EXISTS bands
(
  id INT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id),
  FULLTEXT(name)
);

CREATE TABLE IF NOT EXISTS band_users
(
  user_id INT,
  band_id INT,
  FOREIGN KEY (user_id) references users(id),
  FOREIGN KEY (band_id) references bands(id)
);

CREATE TABLE IF NOT EXISTS tags
(
  id INT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS band_tags
(
  tag_id INT,
  band_id INT,
  FOREIGN KEY (tag_id) references tags(id),
  FOREIGN KEY (band_id) references bands(id)
);

CREATE TABLE IF NOT EXISTS user_tags
(
  tag_id INT,
  user_id INT,
  FOREIGN KEY (tag_id) references tags(id),
  FOREIGN KEY (user_id) references users(id)
);

CREATE TABLE IF NOT EXISTS posts
(
    id INT,
    user_id INT,
    band_id INT,
    text VARCHAR(65535),
    date Timestamp,
    FOREIGN KEY (user_id) references users(id),
    FOREIGN KEY (band_id) references bands(id)
);
