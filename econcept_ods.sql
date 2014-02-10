----------------------------------------------------------------------------------
-- Main Table
-- User Information Table
----------------------------------------------------------------------------------
DROP TABLE IF EXISTS USER_ROLE CASCADE;
DROP TABLE IF EXISTS USER_AUTHORITY CASCADE;
DROP TABLE IF EXISTS USER_ACCOUNT CASCADE;

----------------------------------------------------------------------------------
-- TABLE USER_ACCOUNT
-- Create Table USER to store user account information
----------------------------------------------------------------------------------
CREATE TABLE USER_ACCOUNT
(
USER_ID VARCHAR(60) NOT NULL UNIQUE,
FIRST_NAME VARCHAR (60) NOT NULL,
LAST_NAME VARCHAR (60) NOT NULL,
USER_PASSWORD VARCHAR (64) NOT NULL,
EMAIL VARCHAR(120) NOT NULL UNIQUE,
USER_NON_LOCKED VARCHAR(1) NOT NULL,
CREDENTIALS_EXPIRED VARCHAR(1) NOT NULL,
USER_NON_EXPIRED VARCHAR(1) NOT NULL,
USER_ENABLED VARCHAR(1) NOT NULL,
USER_CREATED_DATE VARCHAR(8) NOT NULL,
USER_LAST_MODIFIED_DATE VARCHAR(8) NOT NULL,
PRIMARY KEY (USER_ID)
);  -- USER_ACCOUNT

----------------------------------------------------------------------------------
-- TABLE USER_AUTHORITY
-- Create Table AUTHORITIES to store authorities information
----------------------------------------------------------------------------------
CREATE TABLE USER_AUTHORITY
(
USER_AUTHORITY_ID VARCHAR(60) NOT NULL UNIQUE,
USER_AUTHORITY_NAME VARCHAR(60) NOT NULL,
PRIMARY KEY (USER_AUTHORITY_ID)
);  -- USER_AUTHORITY


----------------------------------------------------------------------------------
-- TABLE USER_ROLE
-- Create Table USER_ROLES to store user role information
----------------------------------------------------------------------------------
CREATE TABLE USER_ROLE
(
USER_ROLE_ID VARCHAR(60) NOT NULL UNIQUE,
USER_ID VARCHAR(60) NOT NULL REFERENCES USER_ACCOUNT ON DELETE CASCADE ON UPDATE CASCADE,
USER_AUTHORITY_ID VARCHAR(60) NOT NULL REFERENCES USER_AUTHORITY ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY (USER_ROLE_ID),
FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT (USER_ID),
FOREIGN KEY (USER_AUTHORITY_ID) REFERENCES USER_AUTHORITY (USER_AUTHORITY_ID)
);  -- USER_ROLE

----------------------------------------------------------------------------------
-- Sample Data
-- Below is default settings for testing, please do not use during production case
----------------------------------------------------------------------------------

-- Create a Default ROLE_USER
INSERT INTO USER_AUTHORITY
(
USER_AUTHORITY_ID,
USER_AUTHORITY_NAME
)
VALUES
('1', 'ROLE_USER');

-- Create a Default ROLE_ADMIN
INSERT INTO USER_AUTHORITY
(
USER_AUTHORITY_ID,
USER_AUTHORITY_NAME
)
VALUES
('2', 'ROLE_ADMIN');

-- Create a Default admin Account with username admin_username and password admin_password
INSERT INTO USER_ACCOUNT
(
USER_ID,
FIRST_NAME,
LAST_NAME,
USER_PASSWORD,
EMAIL,
USER_NON_LOCKED,
CREDENTIALS_EXPIRED,
USER_NON_EXPIRED,
USER_ENABLED,
USER_CREATED_DATE,
USER_LAST_MODIFIED_DATE
)
VALUES
('admin_username', 'admin_first', 'admin_last','5e9d264ad8afb17c885538320a77dc47e0d6f61168148e76f841537467515620', 'admin@test.com', 'Y', 'N', 'Y', 'Y', '20130707', '20130707');

-- Create a Default USER_ROLE for admin account
INSERT INTO USER_ROLE
(
USER_ROLE_ID,
USER_ID,
USER_AUTHORITY_ID
)
VALUES
('1', 'admin_username', '1');

-- Create a Default USER_ROLE for admin account
INSERT INTO USER_ROLE
(
USER_ROLE_ID,
USER_ID,
USER_AUTHORITY_ID
)
VALUES
('2', 'admin_username', '2');