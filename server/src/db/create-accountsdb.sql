/* DELETE 'accountsDB' database*/
DROP SCHEMA accountsDB;

/* DELETE USER 'sd' AT LOCAL SERVER*/
DROP USER 'accounts_user'@'%';

/* CREATE ''accountsDB' DATABASE */
CREATE SCHEMA accountsDB;

/* CREATE THE USER 'accounts_user' AT LOCAL SERVER WITH PASSWORD 'password' */
CREATE USER 'accounts_user'@'%' IDENTIFIED BY 'password';

/* GRANT FULL ACCESS TO THE DATABASE FOR THE USER 'accounts_user' AT LOCAL SERVER*/
GRANT ALL ON accountsDB.* TO 'accounts_user'@'%';