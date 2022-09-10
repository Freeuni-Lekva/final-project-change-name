
-- Init table
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
    tags_string VARCHAR(4095),
    PRIMARY KEY (id),
    FULLTEXT (first_name, surname, stage_name, tags_string)
);

CREATE TABLE IF NOT EXISTS bands
(
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    tags_string VARCHAR(4095),
    num_members INT,
    PRIMARY KEY (id),
    FULLTEXT(name, tags_string)
);

CREATE TABLE IF NOT EXISTS requests
(
    id INT AUTO_INCREMENT,
    userId INT,
    bandId INT,
    isProcessed BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) references users(id),
    FOREIGN KEY (bandId) references bands(id)
);

CREATE TABLE IF NOT EXISTS invitations
(
    id INT AUTO_INCREMENT,
    userId INT,
    bandId INT,
    isProcessed BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) references users(id),
    FOREIGN KEY (bandId) references bands(id)
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
    id INT AUTO_INCREMENT,
    user_id INT,
    band_id INT,
    is_read BOOLEAN,
    message TEXT,
    date TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) references users(id),
    FOREIGN KEY (band_id) references bands(id)
);

CREATE TABLE IF NOT EXISTS follows
(
    id INT AUTO_INCREMENT,
    follower INT NOT NULL,
    followee INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (follower) REFERENCES users(id),
    FOREIGN KEY (followee) REFERENCES users(id)
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
    id INT AUTO_INCREMENT,
    author_user INT,
    author_band INT,
    text TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY (author_user) references users(id),
    FOREIGN KEY (author_band) references bands(id)
);

CREATE TABLE IF NOT EXISTS band_follows
(
    id INT AUTO_INCREMENT,
    followee_band INT,
    follower_user INT,
    PRIMARY KEY(id),
    FOREIGN KEY(followee_band) references bands(id),
    FOREIGN KEY (follower_user) references users(id)
);

CREATE TABLE IF NOT EXISTS messages
(
    id INT AUTO_INCREMENT,
    sender_id INT,
    receiver_id INT,
    content TEXT,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) references users(id),
    FOREIGN KEY (receiver_id) references users(id),
    PRIMARY KEY (id)
);

-- Handle tag updates
DELIMITER //

CREATE PROCEDURE p_match_user_tags_string_with_tags_table(p_user_id INT)
proc_block: BEGIN
    DECLARE row_exists INT DEFAULT 0;
    DECLARE result_string VARCHAR(4095) DEFAULT '';
    DECLARE current_tag_id INT;
    DECLARE current_tag_name VARCHAR(255);
    DECLARE is_done BOOLEAN DEFAULT FALSE;
    DECLARE cur CURSOR FOR SELECT tag_id FROM user_tags WHERE user_id = p_user_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET is_done = TRUE;

    SELECT EXISTS(SELECT * FROM users WHERE id = p_user_id) INTO row_exists;
    IF row_exists = 0 THEN LEAVE proc_block; END IF;

    OPEN cur;
    concat_strings: LOOP
        FETCH cur INTO current_tag_id;
        IF is_done THEN LEAVE concat_strings; END IF;
        SELECT name INTO current_tag_name FROM tags WHERE id = current_tag_id;
        SET result_string = CONCAT(result_string, ' ', current_tag_name, ',');
    END LOOP;
    CLOSE cur;

    UPDATE users SET tags_string = result_string WHERE id = p_user_id;
END//

CREATE PROCEDURE p_match_band_tags_string_with_tags_table(p_band_id INT)
proc_block: BEGIN
    DECLARE row_exists INT DEFAULT 0;
    DECLARE result_string VARCHAR(4095) DEFAULT '';
    DECLARE current_tag_id INT;
    DECLARE current_tag_name VARCHAR(255);
    DECLARE is_done BOOLEAN DEFAULT FALSE;
    DECLARE cur CURSOR FOR SELECT tag_id FROM band_tags WHERE band_id = p_band_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET is_done = TRUE;

    SELECT EXISTS(SELECT * FROM users WHERE id = p_band_id) INTO row_exists;
    IF row_exists = 0 THEN LEAVE proc_block; END IF;

    OPEN cur;
    concat_strings: LOOP
        FETCH cur INTO current_tag_id;
        IF is_done THEN LEAVE concat_strings; END IF;
        SELECT name INTO current_tag_name FROM tags WHERE id = current_tag_id;
        SET result_string = CONCAT(result_string, ' ', current_tag_name, ',');
    END LOOP;
    CLOSE cur;

    UPDATE bands SET tags_string = result_string WHERE id = p_band_id;
END//

CREATE TRIGGER add_user_tag_trigger AFTER INSERT ON user_tags
FOR EACH ROW
CALL p_match_user_tags_string_with_tags_table(NEW.user_id)//

CREATE TRIGGER remove_user_tag_trigger AFTER DELETE ON user_tags
FOR EACH ROW
CALL p_match_user_tags_string_with_tags_table(OLD.user_id)//

CREATE TRIGGER change_user_tag_trigger AFTER UPDATE ON user_tags
FOR EACH ROW
BEGIN
  CALL p_match_user_tags_string_with_tags_table(OLD.user_id);
  CALL p_match_user_tags_string_with_tags_table(NEW.user_id);
END//

CREATE TRIGGER add_band_tag_trigger AFTER INSERT ON band_tags
FOR EACH ROW
CALL p_match_band_tags_string_with_tags_table(NEW.band_id);

CREATE TRIGGER remove_band_tag_trigger AFTER DELETE ON band_tags
FOR EACH ROW
CALL p_match_band_tags_string_with_tags_table(OLD.band_id)//

CREATE TRIGGER change_band_tag_trigger AFTER UPDATE ON band_tags
FOR EACH ROW
BEGIN
  CALL p_match_band_tags_string_with_tags_table(OLD.band_id);
  CALL p_match_band_tags_string_with_tags_table(NEW.band_id);
END//


CREATE TRIGGER change_tag_trigger AFTER UPDATE ON tags
FOR EACH ROW
BEGIN
    DECLARE is_done BOOLEAN DEFAULT FALSE;
    DECLARE current_id INT;
    DECLARE users_cursor CURSOR FOR SELECT user_id FROM user_tags where user_id = OLD.id;
    DECLARE bands_cursor CURSOR FOR SELECT band_id FROM band_tags WHERE band_id = OLD.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET is_done = TRUE;

    UPDATE user_tags SET tag_id = NEW.id WHERE tag_id = OLD.id;
    UPDATE band_tags SET tag_id = NEW.id WHERE tag_id = OLD.id;

    OPEN users_cursor;
    update_users: LOOP
        FETCH users_cursor INTO current_id;
        IF is_done THEN LEAVE update_users; END IF;
        CALL p_match_user_tags_string_with_tags_table(current_id);
    END LOOP;
    CLOSE users_cursor;

    SET is_done = FALSE;
    OPEN bands_cursor;
    update_bands: LOOP
        FETCH bands_cursor INTO current_id;
        IF is_done THEN LEAVE update_bands; END IF;
        CALL p_match_band_tags_string_with_tags_table(current_id);
    END LOOP;
    CLOSE bands_cursor;
END //

DELIMITER ;