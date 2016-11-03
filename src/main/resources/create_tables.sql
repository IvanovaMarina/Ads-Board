CREATE TABLE IF NOT EXISTS country
(
	id_country TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL,
	PRIMARY KEY(id_country)
);

CREATE TABLE IF NOT EXISTS region
(
	id_region TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL,
	id_country TINYINT UNSIGNED NOT NULL,
	PRIMARY KEY(id_region),
	FOREIGN KEY(id_country)
	REFERENCES country(id_country)
		ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS user
(
	id_user SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	login VARCHAR(30) NOT NULL,
	password VARCHAR(30) NOT NULL,
	path_to_photo VARCHAR(45),
	register_date INT UNSIGNED NOT NULL,
	is_admin BOOLEAN NOT NULL,
	phone VARCHAR(20) NOT NULL,
	email VARCHAR(45) NOT NULL,
	id_region TINYINT UNSIGNED NOT NULL,
	PRIMARY KEY(id_user),
	FOREIGN KEY(id_region)
	REFERENCES region(id_region)
		ON DELETE CASCADE
);