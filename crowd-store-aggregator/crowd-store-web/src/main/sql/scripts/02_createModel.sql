-- PRODUCT_CATEGORY
CREATE TABLE `product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


-- PRODUCT
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


-- PRODUCT_CATEGORY_ASSOC
CREATE TABLE `product_category_assoc` (
  `product_id` bigint(10) NOT NULL,
  `product_category_id` bigint(10) NOT NULL,
  PRIMARY KEY (`product_id`, `product_category_id`),
  KEY `product_id` (`product_id`),
  KEY `product_category_id` (`product_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `product_category_assoc`
  ADD CONSTRAINT `product_category_assoc_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `product_category_assoc_ibfk_2` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`);

-- USER
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `logically_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `private_token` varchar(40) NOT NULL,
  `public_token` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `private_token` (`private_token`),
  UNIQUE KEY `public_token` (`public_token`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


-- STORE
CREATE TABLE `store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `logically_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


-- SELLING_PRODUCT
CREATE TABLE `selling_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  `price` bigint(10) NOT NULL,
  `end_date` datetime NOT NULL,
  `logically_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `store_id` (`store_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `selling_product`
  ADD CONSTRAINT `selling_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `selling_product_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);


-- USER_STORE
CREATE TABLE `user_store` (
  `user_id` bigint(10) NOT NULL,
  `store_id` bigint(10) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`, `store_id`),
  KEY `user_id` (`user_id`),
  KEY `store_id` (`store_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `user_store`
  ADD CONSTRAINT `user_store_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `user_store_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);


-- SELLING_ORDER
CREATE TABLE `selling_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `selling_product_id` bigint(20) NOT NULL,
  `quantity` bigint(10) NOT NULL,
  `order_date` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `selling_product_id` (`selling_product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `selling_order`
  ADD CONSTRAINT `selling_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `selling_order_ibfk_2` FOREIGN KEY (`selling_product_id`) REFERENCES `selling_product` (`id`);


-- BUYING ORDER
CREATE TABLE `buying_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` bigint(10) NOT NULL,
  `unit_price` bigint(10) NOT NULL,
  `order_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `store_id` (`store_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `buying_order`
  ADD CONSTRAINT `buying_order_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
  ADD CONSTRAINT `buying_order_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

-- DEBT
CREATE TABLE `debt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `debtor_id` bigint(20) NOT NULL,
  `creditor_id` bigint(20) NOT NULL,
  `amount` bigint(10) NOT NULL,
  `debt_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `debtor_id` (`debtor_id`),
  KEY `creditor_id` (`creditor_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `debt`
  ADD CONSTRAINT `debt_ibfk_1` FOREIGN KEY (`debtor_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `debt_ibfk_2` FOREIGN KEY (`creditor_id`) REFERENCES `user` (`id`);

-- CREDIT
CREATE TABLE `credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  `amount` bigint(10) NOT NULL,
  `credit_date` datetime NOT NULL,
  `token` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `store_id` (`store_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

ALTER TABLE `credit`
  ADD CONSTRAINT `credit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `credit_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);

-- TOKEN
-- I18N