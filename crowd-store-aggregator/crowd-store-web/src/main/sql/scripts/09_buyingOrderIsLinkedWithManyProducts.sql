ALTER TABLE `buying_order` DROP FOREIGN KEY `buying_order_ibfk_2`;
ALTER TABLE `buying_order` DROP KEY `product_id`;
ALTER TABLE `buying_order` DROP `product_id`;

CREATE TABLE `buying_order_product` (
  `product_id` bigint(10) NOT NULL,
  `buying_order_id` bigint(10) NOT NULL,
  PRIMARY KEY (`product_id`, `buying_order_id`),
  KEY `product_id` (`product_id`),
  KEY `buying_order_id` (`buying_order_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 ;

ALTER TABLE `buying_order_product`
  ADD CONSTRAINT `buying_order_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `buying_order_product_ibfk_2` FOREIGN KEY (`buying_order_id`) REFERENCES `buying_order` (`id`);
