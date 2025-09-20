CREATE DATABASE librarydb;
CREATE USER 'library_user'@'%' IDENTIFIED BY 'secret';
GRANT ALL PRIVILEGES ON librarydb.* TO 'library_user'@'%';