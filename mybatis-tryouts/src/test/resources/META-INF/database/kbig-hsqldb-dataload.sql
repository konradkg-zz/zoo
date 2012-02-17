INSERT INTO roles VALUES(DEFAULT, 'default role', 'desc default role', 1, '2009-12-16 14:22:22', '2011-05-17 19:13:23');
INSERT INTO roles VALUES(DEFAULT, 'role1', 'desc role1', 1, '2010-12-16 14:22:22', '2011-05-17 19:13:23');

INSERT INTO payment_experiences VALUES(1, 10000000000, 1, 'refNo1', 0, 1, 0, 'entitlment text', 'ent: doc ref no', 20, 2000.20, 1000.01, 1, 500.40, '2002-02-05', null, '2002-10-19', null, 1, 'info text', 'enforcable ref no', 'enorc court info', '2003-08-19', 10, 1, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO pex_legal_entities VALUES(1, 1, 1, 'refNo1', 0, 0, 2, '67021910003', '6551402430', '0002222', 'ATM999666', '7776654', '88888111111188', 3, 'for registry', 'for:1002000', 'reperesent', 2, 'indust other', null, 'KBIG SA', 'ul. Lublanska', '36', null, '18-100', 'Busko-Zdrój', 3, 'ul. Dluga', '15', '45', '31-100', 'Katowice', 3, 1,  '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO pex_legal_entities VALUES(2, 1, 1, 'refNo1', 1, 0, 1, '97021910003', null, null, 'ATM999664', null, null, null, null, null, null, 0, null, 'jan', 'Kowalski', 'ul. Lokietka', '135A', '45', '28-100', 'Busko-Zdrój', 3, 'ul. Dluga', '15', '45', '31-100', 'Katowice', 3, 1,  '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(1, 1, 1, 'refNo1', 0, '67021910003', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(2, 1, 1, 'refNo1', 1, '6551402430', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(3, 1, 1, 'refNo1', 2, '0002222', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(4, 1, 1, 'refNo1', 3, 'ATM999666', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(5, 1, 1, 'refNo1', 4, '7776654', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(6, 1, 1, 'refNo1', 5, '88888111111188', 3, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(7, 1, 1, 'refNo1', -1, 'for:1002000', 3, 'for registry', '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(8, 2, 1, 'refNo1', 0, '97021910003', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
INSERT INTO identification_numbers VALUES(9, 2, 1, 'refNo1', 3, 'ATM999664', 0, null, '2012-01-02 00:01:00', '2012-02-16 11:22:22', '2012-02-17 09:23:23');
