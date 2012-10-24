DROP TABLE `selling_order_product`;

ALTER TABLE `selling_order` ADD COLUMN `selling_product_id` bigint(20) NOT NULL;
ALTER TABLE `selling_order` ADD CONSTRAINT `selling_order_ibfk_2` FOREIGN KEY (`selling_product_id`) REFERENCES `selling_product` (`id`);