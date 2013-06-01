-- Drop existing table if one exist
DROP TABLE IF EXISTS account;

-- Create Table account to store user account information
CREATE TABLE account
(
accountid VARCHAR (30) NOT NULL,
firstname VARCHAR (30) NOT NULL,
lastname VARCHAR (30) NOT NULL,
accountpassword VARCHAR (30) NOT NULL,
email VARCHAR(60) NOT NULL,
accountstatus VARCHAR(30) NOT NULL
);
