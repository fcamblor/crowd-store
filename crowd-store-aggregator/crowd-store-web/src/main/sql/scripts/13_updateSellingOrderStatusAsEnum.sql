ALTER TABLE `selling_order` MODIFY status ENUM('WAITING_FOR_VALIDATION','VALIDATED','REJECTED') NOT NULL;
