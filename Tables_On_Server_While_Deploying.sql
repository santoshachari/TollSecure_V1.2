CREATE USER 'seoni_user'@'%' IDENTIFIED BY 'seoni_password';

GRANT ALL ON seoni_db.* TO 'seoni_user';

create database seoni_db;

use seoni_db;

/*Table 1 T_SHIFT*/

create table T_SHIFT(SHIFT_ID INT  NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
SHIFT_DESC VARCHAR(500),
START_TIME  DATETIME,
END_TIME DATETIME,
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT ,
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY(SHIFT_ID)); 

alter table T_SHIFT modify start_time time;
alter table T_SHIFT modify end_time time;

alter table seoni_db.T_SHIFT add column STATUS_FLAG varchar(20);


/*Vehicle class*/

create table T_VEHICLE_CLASS(VEHICLE_CLASS_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
VEHICLE_CLASS  VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY(VEHICLE_CLASS_ID));

/*T_USER*/

CREATE TABLE T_USER(USER_ID INT  NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
USER_FIRST_NAME  VARCHAR(100),
USER_LAST_NAME VARCHAR(100),
USER_MOBILE_NO INT,
DATE_JOINED  DATE,
USER_ROLE  VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY(USER_ID));

alter table T_USER add column USER_PASSWORD varchar(100) after USER_LAST_NAME;

alter table T_USER drop USER_MOBILE_NO;

ALTER TABLE T_USER ADD USER_MOBILE_NO BIGINT AFTER USER_PASSWORD;

Alter table T_USER AUTO_INCREMENT = 10000;

/*T_LANE*/

CREATE TABLE T_LANE(LANE_ID  INT  NOT NULL auto_increment PRIMARY KEY,
TOLL_PLAZA_ID  INT,
LANE_DIRECTION VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME);

alter table T_LANE add column USER_ID INT(10);

alter table T_LANE add column LANE_CODE varchar(20) after LANE_DIRECTION;

alter table T_LANE add column STATUS_FLAG varchar(20) after LANE_CODE; 

/*Toll config*/
create table T_TOLL_CONFIG(VEHICLE_CLASS_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
JOURNEY_TYPE   VARCHAR(100),
STANDARD_TOLL   DECIMAL(10,4),
CONCESSION_TYPE   VARCHAR(100),
CONCESSION_PCT   DECIMAL(10,4),
CONCESSION_AMT  DECIMAL(10,4),
TOLL_AMT  DECIMAL(10,4), 
EFFECTIVE_FROM  VARCHAR(100),
EFFECTIVE_TO VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (VEHICLE_CLASS_ID));

Alter table T_TOLL_CONFIG add column VEHICLE_CLASS varchar(100) after VEHICLE_CLASS_ID;

alter table seoni_db.T_TOLL_CONFIG add column STATUS_FLAG varchar(100);

/*T_SHIFT_TRANSACTION*/

create table T_SHIFT_TRANSACTION
(SHIFT_TRANSACTION_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
LANE_ID  INT,
USER_ID  INT,
SHIFT_ID  INT,
PUNCH_IN_TIME  DATETIME,
PUNCH_OUT_TIME DATETIME,
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (SHIFT_TRANSACTION_ID));

/*T_TOLL_TRANSACTION*/

create table T_TOLL_TRANSACTION(TRANSACTION_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
TRANSACTION_DATE  DATE,
TRANSACTION_TIMESTAMP  DATETIME,
SHIFT_TRANSACTION_ID  INT,
VEHICLE_NUMBER  VARCHAR(100),
LANE_ID  INT,
USER_ID  INT,
SHIFT_ID  INT,
VEHICLE_CLASS_ID  INT,
JOURNEY_TYPE  VARCHAR(100),
CONCESSION_TYPE  VARCHAR(100),
TOLL_AMT  DECIMAL(10,4),
PARENT_TRANSACTION_ID  INT,
RECORD_IND VARCHAR(100),
COMMENTS  VARCHAR(500), 
CANCELLATION_CODE  VARCHAR(100),
IMAGE_BLOB BLOB,
VIDEO_BLOB BLOB,
PAYMENT_METHOD  VARCHAR(100),
CLIENT_ID  INT,
CLIENT_IP_ADDRESS  VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (TRANSACTION_ID));

alter table T_TOLL_TRANSACTION add column SHIFT_DESC varchar(1);

Alter table T_TOLL_TRANSACTION AUTO_INCREMENT = 1001;

alter table T_TOLL_TRANSACTION add Img_Captured_YN varchar (3); 

/*update T_TOLL_TRANSACTION set Img_Captured_YN = 'Y' where TRANSACTION_ID >= 1001;*/

/*T_CONCESSION_VEHICLES*/

create table T_CONCESSION_VEHICLES( VEHICLE_NUMBER	VARCHAR(100),
RECORD_ID INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
CONCESSION_CATEGORY	VARCHAR(100),
CONCESSION_PCT	DECIMAL(10,4),
CONCESSION_AMT	DECIMAL(10,4),
START_DATE	DATE,
END_DATE	DATE,
COMMENTS	VARCHAR(100),
CREATE_TIMESTAMP	DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (RECORD_ID));

/*T_TOLL_PLAZA*/

create table T_TOLL_PLAZA(TOLL_PLAZA_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_NAME  VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (TOLL_PLAZA_ID) );

/*T_CASHUP*/

create table T_CASHUP(USER_ID INT,
SHIFT_ID INT,
TOLL_PLAZA_ID INT,
ACCOUNTANT_ID VARCHAR(20),
CHECK_DATE DATE,
TOTAL_AMOUNT DECIMAL(10,4),
SYSTEM_AMOUNT DECIMAL(10,4),
SHORTAGE_AMOUNT DECIMAL(10,4),
EXCESS_AMOUNT  DECIMAL(10,4),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME);

alter table T_CASHUP add column `CASHUP_ID` int(10) unsigned primary KEY AUTO_INCREMENT;

alter table T_CASHUP add column LANE_ID INT(10) after TOLL_PLAZA_ID;

/*T_CASHUP_DECLARATION*/

create table T_CASHUP_DECLARATION(USER_ID INT,
TOLL_PLAZA_ID INT,
SHIFT_ID INT,
LANE_ID INT,
D_2000  INT,
D_500 INT,
D_200 INT,
D_100 INT,
D_50 INT,
D_20 INT,
D_10 INT,
D_5 INT,
D_2 INT,
D_1 INT,
TOTAL_COUNT INT,
TOTAL_AMOUNT INT,
CHECK_DATE DATE,
ACCOUNTANT_ID VARCHAR(20),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME);

alter table T_CASHUP_DECLARATION add column `DECLARATION_ID` int(10) unsigned primary KEY AUTO_INCREMENT;

alter table T_CASHUP_DECLARATION add column SHIFT_DESC varchar(100);

alter table T_CASHUP_DECLARATION add column D_1000 Int(10) after D_2000;

/*T_FLOAT_AMOUNT_DETAILS*/

create table T_FLOAT_AMOUNT_DETAILS
(FLOAT_DETAIL_ID  INT NOT NULL auto_increment,
TOLL_PLAZA_ID INT,
SHIFT_ID INT,
LANE_ID INT,
USER_ID INT,
T_DATE DATE,
FLOAT_AMOUNT DECIMAL(10,4),
ACCOUNTANT_ID VARCHAR(10),
STATUS CHAR(1),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY(FLOAT_DETAIL_ID));

use seoni_db;

/*T_VEHICLE_TRACKING*/

use seoni_db;

create table T_VEHICLE_TRACKING 
( TRACKING_ID INT(10) NOT NULL auto_increment,
VEHICLE_NUMBER VARCHAR(100),
LANE_DIRECTION varchar(500),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT(10),
MODIFICATION_TIMESTAMP DATETIME,
MODIFIED_USER_ID INT(10),
PRIMARY KEY (TRACKING_ID)
);

ALTER TABLE T_VEHICLE_TRACKING ADD INDEX (VEHICLE_NUMBER);

alter table T_VEHICLE_TRACKING add column VEHICLE_CLASS_ID INT(100) after LANE_DIRECTION;

alter table T_VEHICLE_TRACKING add column JOURNEY_TYPE varchar(100);

/*T_PASS_CONFIGURATION*/
use seoni_db;

create table T_PASS_CONFIGURATION 
(PASS_CONFIGURATION_ID INT(10) NOT NULL auto_increment,
TOLL_PLAZA_ID INT(10),
VEHICLE_CLASS_ID INT(100),
PASS_TYPE varchar(500),
AMT float(10,2),
EFFECTIVE_FROM DATETIME,
EFFECTIVE_TO DATETIME,
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT(10),
MODIFICATION_TIMESTAMP DATETIME,
MODIFIED_USER_ID INT(10),
PRIMARY KEY (PASS_CONFIGURATION_ID)
);

alter table T_PASS_CONFIGURATION add column STATUS_FLAG varchar(100);

alter table T_PASS_CONFIGURATION add column VEHICLE_CLASS varchar(100);

/*T_PASS*/

create table T_PASS(VEHICLE_NUMBER	VARCHAR(100),
RECORD_ID INT NOT NULL auto_increment,
TOLL_PLAZA_ID  INT,
VEHICLE_CLASS	VARCHAR(100),
PERIOD	VARCHAR(100),
START_DATE	DATE,
END_DATE	DATE,
AMOUNT	DECIMAL(10,4),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY (RECORD_ID));

alter table T_PASS add column PASS_TYPE varchar(100);

ALTER TABLE T_PASS ADD INDEX (VEHICLE_NUMBER);

ALTER TABLE T_TOLL_TRANSACTION ADD INDEX (VEHICLE_NUMBER);

alter table T_TOLL_TRANSACTION add column TICKET_CODE VARCHAR(100) after TRANSACTION_ID;

ALTER TABLE T_TOLL_TRANSACTION ADD INDEX (TICKET_CODE);

--new ones after deployment -- 1 oct 2017 after returning from Himmatnagar
alter table T_TOLL_TRANSACTION MODIFY IMAGE_BLOB MEDIUMBLOB;

alter table T_TOLL_TRANSACTION add column IMAGE_ADDRESS varchar(100);

alter table T_USER add column STATUS_FLAG varchar(25);

update T_USER set STATUS_FLAG = "ACTIVE" where user_id <= 50;

alter table T_PASS add column trip_count int(11);

alter table T_PASS add column trip_validity int(11);


--new ones after images also -- 27th Oct 2017 

ALTER TABLE T_PASS change RECORD_ID PASS_ID INT NOT NULL auto_increment;

ALTER TABLE T_TOLL_TRANSACTION
ADD PASS_ID  INT;

ALTER TABLE T_TOLL_TRANSACTION
ADD Exempt_ID  INT;

create table T_EXEMPT(EXEMPT_ID INT NOT NULL auto_increment,
TOLL_PLAZA_ID INT,
EXEMPT_TYPE VARCHAR(100),
CREATE_TIMESTAMP DATETIME,
CREATE_USER_ID INT, 
MODIFIED_USER_ID INT, 
MODIFICATION_TIMESTAMP DATETIME,
PRIMARY KEY(EXEMPT_ID));

-- 31st Oct

ALTER TABLE T_TOLL_TRANSACTION add concession_id int;

rename table T_CONCESSION_VEHICLES to T_CONCESSION;

alter table T_CONCESSION change RECORD_ID CONCESSION_ID int not null auto_increment;

alter table T_EXEMPT add column VEHICLE_NUMBER varchar(30) after EXEMPT_ID;

--8th Nov 
alter table T_PASS add column CANCELLATION_CODE varchar(20);

alter table T_PASS add column REMARKS varchar(100);
