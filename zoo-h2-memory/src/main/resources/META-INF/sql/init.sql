
CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
CALL FTL_INIT();


CREATE TABLE PEX(
	id IDENTITY PRIMARY KEY, 
	pex_id INT, 
	pex_case_ref_no NVARCHAR(255), 
	pex_can_publish_creditor_data BOOLEAN, 
	pex_entitlement_text NVARCHAR(255), 
	pex_entitlement_document_ref_no NVARCHAR(255), 
	pex_icur_id INT, 
	pex_amount_total DECIMAL(20, 2), 
	pex_amount_open DECIMAL(20, 2), 
	pex_date_due DATE, 
	pex_date_modified DATETIME,

	debtor_first_name NVARCHAR(255), 
	debtor_name NVARCHAR(255), 
	debtor_nip NVARCHAR(255), 
	debtor_pesel NVARCHAR(255), 
	debtor_regon NVARCHAR(255), 
	debtor_street NVARCHAR(255), 
	debtor_house_number NVARCHAR(255), 
	debtor_flat_number NVARCHAR(255), 
	debtor_zip NVARCHAR(255), 
	debtor_ic_id INT,

	creditor_first_name NVARCHAR(255), 
	creditor_name NVARCHAR(255), 
	creditor_nip NVARCHAR(255), 
	creditor_pesel NVARCHAR(255), 
	creditor_regon NVARCHAR(255), 
	creditor_street NVARCHAR(255), 
	creditor_house_number NVARCHAR(255), 
	creditor_flat_number NVARCHAR(255), 
	creditor_zip NVARCHAR(255), 
	creditor_ic_id INT
);

CALL FTL_CREATE_INDEX('PUBLIC', 'PEX', 'DEBTOR_FIRST_NAME,DEBTOR_NAME,DEBTOR_STREET');





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