INSERT INTO user(email,password,first_name,last_name,logically_deleted,private_token,public_token)
VALUES
(
  't@t.fr',
  'toto',
  'Damien',
  'RICCIO',
  FALSE,
  '0',
  '0'
);

INSERT INTO store
(
  name,
  logically_deleted
)
VALUES
(
  '4sh store',
  FALSE
);

INSERT INTO user_store
(
  user_id,
  store_id,
  role
)
SELECT u.id, s.id, 'CUSTOMER'
FROM user u, store s
WHERE u.email='t@t.fr' and s.name='4sh store'
;