----------------------------------------------------------------------------------
-- Main Table
-- User Information Table
----------------------------------------------------------------------------------
DROP TABLE IF EXISTS USER_ROLES CASCADE;
DROP TABLE IF EXISTS USER_AUTHORITIES CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;

----------------------------------------------------------------------------------
-- TABLE USER
-- Create Table USER to store user account information
----------------------------------------------------------------------------------
CREATE TABLE USERS
(
USER_ID VARCHAR(40) NOT NULL UNIQUE,
USER_NAME VARCHAR(60) NOT NULL UNIQUE,
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
);  -- USERS

----------------------------------------------------------------------------------
-- TABLE USER_AUTHORITIES
-- Create Table AUTHORITIES to store authorities information
----------------------------------------------------------------------------------
CREATE TABLE USER_AUTHORITIES
(
USER_AUTHORITY_ID VARCHAR(60) NOT NULL UNIQUE,
USER_AUTHORITY_NAME VARCHAR(60) NOT NULL,
PRIMARY KEY (USER_AUTHORITY_ID)
);  -- USER_AUTHORITIES


----------------------------------------------------------------------------------
-- TABLE USER_ROLES
-- Create Table USER_ROLES to store user role information
----------------------------------------------------------------------------------
CREATE TABLE USER_ROLES
(
USER_ROLE_ID VARCHAR(60) NOT NULL UNIQUE,
USER_ID VARCHAR(60) NOT NULL REFERENCES USERS ON DELETE CASCADE ON UPDATE CASCADE,
USER_AUTHORITY_ID VARCHAR(60) NOT NULL REFERENCES USER_AUTHORITIES ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY (USER_ROLE_ID),
FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID),
FOREIGN KEY (USER_AUTHORITY_ID) REFERENCES USER_AUTHORITIES (USER_AUTHORITY_ID)
);  -- USER_ROLES

----------------------------------------------------------------------------------
-- Sample Data
-- Below is default settings for testing, please do not use during production case
----------------------------------------------------------------------------------

-- Create a Default ROLE_USER
INSERT INTO USER_AUTHORITIES
(
USER_AUTHORITY_ID,
USER_AUTHORITY_NAME
)
VALUES
('1', 'ROLE_USER');

-- Create a Default ROLE_ADMIN
INSERT INTO USER_AUTHORITIES
(
USER_AUTHORITY_ID,
USER_AUTHORITY_NAME
)
VALUES
('2', 'ROLE_ADMIN');

-- Create a Default admin Account with username admin_username and password admin_password
INSERT INTO USERS
(
USER_ID,
USER_NAME,
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
('1e3591fca0efaa77fde9af49bff84b05f8975a38', 'admin_username', 'admin_first', 'admin_last','5e9d264ad8afb17c885538320a77dc47e0d6f61168148e76f841537467515620', 'admin@test.com', 'Y', 'N', 'Y', 'Y', '20130707', '20130707');

-- Create a Default USER_ROLE for admin account
INSERT INTO USER_ROLES
(
USER_ROLE_ID,
USER_ID,
USER_AUTHORITY_ID
)
VALUES
('1', '1e3591fca0efaa77fde9af49bff84b05f8975a38', '1');

-- Create a Default USER_ROLE for admin account
INSERT INTO USER_ROLES
(
USER_ROLE_ID,
USER_ID,
USER_AUTHORITY_ID
)
VALUES
('2', '1e3591fca0efaa77fde9af49bff84b05f8975a38', '2');