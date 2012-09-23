
drop procedure if exists `convertTablesToUTF8`;
drop procedure if exists `convertTableToUTF8`;
drop procedure if exists `setDefaultCharsetAsUTF8`;
drop procedure if exists `convertToInnoDb`;

CREATE PROCEDURE convertToInnoDb(tname VARCHAR(255))
BEGIN
  SET @q = CONCAT('ALTER TABLE `', tname, '` ENGINE InnoDB');
  PREPARE stmt FROM @q;
  EXECUTE stmt;

  DEALLOCATE PREPARE stmt;
END;

/

CREATE PROCEDURE convertTableToUTF8(tname VARCHAR(255))
BEGIN
  SET @q = CONCAT('ALTER TABLE `', tname, '` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci');
  PREPARE stmt FROM @q;
  EXECUTE stmt;

  DEALLOCATE PREPARE stmt;
END;

/

CREATE PROCEDURE setDefaultCharsetAsUTF8(tname VARCHAR(255))
BEGIN
  SET @q = CONCAT('ALTER TABLE `', tname, '` DEFAULT CHARACTER SET ''utf8'' COLLATE ''utf8_general_ci''');
  PREPARE stmt FROM @q;
  EXECUTE stmt;

  DEALLOCATE PREPARE stmt;
END;

/

CREATE PROCEDURE convertTablesToUTF8()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE tname VARCHAR(64);

  DECLARE non_utf8_tnames_cur CURSOR FOR
      SELECT TABLE_NAME
      FROM information_schema.columns
      WHERE TABLE_SCHEMA = DATABASE()
      -- Iterating only on tables different from utf8 because conversion takes time !
      AND collation_name != 'utf8_general_ci';
  DECLARE non_innodb_tnames_cur CURSOR FOR
      SELECT TABLE_NAME
      FROM INFORMATION_SCHEMA.TABLES
      WHERE TABLE_SCHEMA = DATABASE() AND engine = 'MyISAM';
  DECLARE tnames_cur CURSOR FOR
      SELECT TABLE_NAME
      FROM INFORMATION_SCHEMA.TABLES
      WHERE TABLE_SCHEMA = DATABASE();


  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN non_utf8_tnames_cur;

  SET FOREIGN_KEY_CHECKS = 0;

  SET done = FALSE;
  read_loop: LOOP
    FETCH non_utf8_tnames_cur INTO tname;
    IF done THEN
      LEAVE read_loop;
    END IF;

    CALL convertTableToUTF8(tname);
  END LOOP;

  CLOSE non_utf8_tnames_cur;

  SET FOREIGN_KEY_CHECKS = 1;


  OPEN tnames_cur;

  SET done = FALSE;
  read_loop: LOOP
    FETCH tnames_cur INTO tname;
    IF done THEN
      LEAVE read_loop;
    END IF;

    CALL setDefaultCharsetAsUTF8(tname);
  END LOOP;

  CLOSE tnames_cur;


  OPEN non_innodb_tnames_cur;

  SET done = FALSE;
  read_loop: LOOP
    FETCH non_innodb_tnames_cur INTO tname;
    IF done THEN
      LEAVE read_loop;
    END IF;

    CALL convertToInnoDb(tname);
  END LOOP;

  CLOSE non_innodb_tnames_cur;

  ALTER DATABASE DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_general_ci';
END;

/
