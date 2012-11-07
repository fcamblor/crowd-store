-- non hashed password: toto
INSERT IGNORE INTO `user`(email,hashed_password,first_name,last_name,logically_deleted,private_token)
VALUES
(
  't@t.fr',
  '10e06b990d44de0091a2113fd95c92fc905166af147aa7632639c41aa7f26b1620c47443813c605b924c05591c161ecc35944fc69c4433a49d10fc6b04a33611',
  'Damien',
  'RICCIO',
  FALSE,
  '0'
);

INSERT IGNORE INTO store (name, logically_deleted)
VALUES ('4sh store', FALSE);
INSERT IGNORE INTO store (name, logically_deleted)
VALUES ('4sh soccer5', FALSE);

INSERT IGNORE INTO `user_store` (user_id, store_id, role)
SELECT u.id, s.id, 'CUSTOMER'
FROM user u, store s
WHERE u.email='t@t.fr' and s.name='4sh store';
INSERT IGNORE INTO `user_store` (user_id, store_id, role)
SELECT u.id, s.id, 'ADMIN'
FROM user u, store s
WHERE u.email='t@t.fr' and s.name='4sh soccer5';


INSERT IGNORE INTO product_category (name) VALUES ('Gazeux');
INSERT IGNORE INTO product_category (name) VALUES ('Naturel');
INSERT IGNORE INTO product_category (name) VALUES ('Chimique');


INSERT IGNORE INTO product (name, description) VALUES ('Liptonic', 'Thé glacé gazeux');
INSERT IGNORE INTO product (name, description) VALUES ('IceTea', 'Thé glacé');

INSERT IGNORE INTO product_category_assoc (product_id, product_category_id)
            select p.id, pc.id from product p, product_category pc where p.name='Liptonic' and pc.name='Gazeux';
INSERT IGNORE INTO product_category_assoc (product_id, product_category_id)
            select p.id, pc.id from product p, product_category pc where p.name='IceTea' and pc.name='Naturel';


INSERT IGNORE INTO selling_product (product_id, store_id, price)
            select p.id, s.id, 2 from product p, store s where p.name='Liptonic' and s.name='4sh store';
INSERT IGNORE INTO selling_product (product_id, store_id, price)
            select p.id, s.id, 3 from product p, store s where p.name='IceTea' and s.name='4sh store';
