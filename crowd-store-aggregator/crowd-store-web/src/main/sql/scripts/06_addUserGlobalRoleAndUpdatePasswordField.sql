ALTER TABLE  `user` ADD  `global_role` ENUM(  'ADMIN',  'SIMPLE_USER' ) NOT NULL DEFAULT  'SIMPLE_USER' AFTER  `last_name`;
ALTER TABLE  `user` CHANGE  `password`  `hashed_password` VARCHAR( 128 ) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;
