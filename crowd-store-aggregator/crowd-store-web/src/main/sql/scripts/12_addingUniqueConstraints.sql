ALTER TABLE  `product_category` ADD UNIQUE (
`name`
);

ALTER TABLE  `product` ADD UNIQUE (
`name`
);

ALTER TABLE  `selling_product` ADD UNIQUE (
`product_id` ,
`store_id`
);
