ALTER TABLE `selling_order` DROP FOREIGN KEY `selling_order_ibfk_2`;
ALTER TABLE `selling_order` DROP KEY `selling_product_id`;
ALTER TABLE `selling_order` DROP `selling_product_id`;

CREATE TABLE `selling_order_product` (
  `product_id` bigint(10) NOT NULL,
  `selling_product_id` bigint(10) NOT NULL,
  PRIMARY KEY (`product_id`, `selling_product_id`),
  KEY `product_id` (`product_id`),
  KEY `selling_product_id` (`selling_product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 ;

ALTER TABLE `selling_order_product`
  ADD CONSTRAINT `selling_order_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `selling_order_product_ibfk_2` FOREIGN KEY (`selling_product_id`) REFERENCES `selling_product` (`id`);
