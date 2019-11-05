DROP TABLE USERINFO;

CREATE TABLE USERINFO ( 
	userId          varchar(12)		PRIMARY KEY, 
	password		varchar(12)		NOT NULL,
	name			varchar(20)		NOT NULL,
	email			varchar(50),	
 	phone			varchar(20)
);

INSERT INTO USERINFO VALUES('admin', 'admin', '시스템관리자', 'admin@dbserver.dongduk.ac.kr', '02-940-9999');
