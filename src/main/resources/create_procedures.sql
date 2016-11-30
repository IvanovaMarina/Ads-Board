CREATE DEFINER=`root`@`localhost` PROCEDURE `add_advert`(
	IN in_title VARCHAR(60), IN in_description TEXT, 
	IN in_path_to_photo VARCHAR(45), IN in_add_time INT, 
    IN in_id_user SMALLINT, IN in_id_scat SMALLINT,
    IN in_id_m SMALLINT,
    IN in_amount DECIMAL(10,2), IN in_id_cur SMALLINT
)
BEGIN

DECLARE id INT;

INSERT INTO advert(title, description, path_to_photo, 
	add_time, views, id_user, id_scat, id_m)
VALUES(in_title, in_description, in_path_to_photo, 
	in_add_time, 0, in_id_user, in_id_scat, in_id_m);

SELECT id_ad INTO @id FROM advert WHERE title = in_title  AND add_time = in_add_time LIMIT 1;

INSERT INTO price(id_p, amount, id_cur)
VALUES (@id, in_amount, in_id_cur);

UPDATE advert SET id_p = @id
WHERE id_ad = @id;

END