
CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
CALL FTL_INIT();


--INSERT INTO PEX (PEX_ID, DEBTOR_FIRST_NAME) VALUES(1, 'Hello World');




--INSERT INTO TEST VALUES(1, 'Hello World');
--INSERT INTO TEST VALUES(2, 'Hello World1');
--INSERT INTO TEST VALUES(3, 'Dupa');


--CREATE TABLE TEST_TEMP(ID INT PRIMARY KEY, NAME VARCHAR);
--CALL FTL_CREATE_INDEX('PUBLIC', 'TEST_TEMP', NULL);


--DROP TABLE TEST;
--ALTER TABLE TEST_TEMP RENAME TO TEST;
--CALL FTL_REINDEX();

--FTL_CREATE_INDEX(schemaNameString, tableNameString, columnListString)
--FTL_SEARCH(queryString, limitInt, offsetInt): result set
--FTL_REINDEX()
--FTL_DROP_ALL()
--