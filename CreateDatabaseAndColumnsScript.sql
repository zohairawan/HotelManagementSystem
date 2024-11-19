CREATE DATABASE IF NOT EXISTS HOTEL_DB;

USE HOTEL_DB;

CREATE TABLE Guest (
	guest_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) not null,
    last_name VARCHAR(255) not null,
    contact_number VARCHAR(255) not null,
    email VARCHAR(255),
    total_number_of_guests INT
);

CREATE TABLE Room (
	room_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    guest_id INT,
    check_in_date DATETIME,
    check_out_date DATETIME,
    actual_check_in_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actual_check_out_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    room_number INT NOT NULL,
    number_of_beds INT,
    available TINYINT,
    FOREIGN KEY(guest_id) REFERENCES Guest(guest_id)
);