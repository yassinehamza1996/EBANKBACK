-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 14, 2024 at 07:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_operation`
--

CREATE TABLE `account_operation` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `operation_date` datetime(6) DEFAULT NULL,
  `type` enum('CREDIT','DEBIT') DEFAULT NULL,
  `bank_account_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account_operation`
--

INSERT INTO `account_operation` (`id`, `amount`, `description`, `operation_date`, `type`, `bank_account_id`) VALUES
(1, 10000.00, 'ok', '2024-08-14 14:49:50.000000', 'DEBIT', '20231000001'),
(2, 20000.00, 'salaire', '2024-08-14 14:50:43.000000', 'CREDIT', '20231000001'),
(3, 10000.00, 'Transfer to : 20231000002', '2024-08-14 15:11:10.000000', 'DEBIT', '20231000001'),
(4, 10000.00, 'Transfer to : 20231000002', '2024-08-14 15:12:08.000000', 'DEBIT', '20231000001'),
(5, 10000.00, 'Transfer from : 20231000001', '2024-08-14 15:12:08.000000', 'CREDIT', '20231000002'),
(6, 10000.00, 'Transfer to : 20231000002', '2024-08-14 15:15:07.000000', 'DEBIT', '20231000001'),
(7, 10000.00, 'Transfer from : 20231000001', '2024-08-14 15:15:07.000000', 'CREDIT', '20231000002'),
(8, 10000.00, 'retrait', '2024-08-14 15:15:36.000000', 'DEBIT', '20231000002'),
(9, 10000.00, 'salaire', '2024-08-14 15:15:55.000000', 'CREDIT', '20231000002');

-- --------------------------------------------------------

--
-- Table structure for table `bank_account`
--

CREATE TABLE `bank_account` (
  `type` varchar(4) NOT NULL,
  `id` varchar(255) NOT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `status` enum('ACTIVATED','CREATED','SUSPENDED') DEFAULT NULL,
  `over_draft` double DEFAULT NULL,
  `interest_rate` double DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bank_account`
--

INSERT INTO `bank_account` (`type`, `id`, `balance`, `created_at`, `status`, `over_draft`, `interest_rate`, `customer_id`) VALUES
('CA', '20231000000', 1000.00, '2024-08-13 18:40:25.000000', 'CREATED', 500, NULL, 1),
('CA', '20231000001', 30000.00, '2024-08-14 13:19:28.000000', 'ACTIVATED', 10, NULL, 2),
('CA', '20231000002', 50000.00, '2024-08-14 15:10:00.000000', 'ACTIVATED', 1, NULL, 3),
('CA', '20231000003', 50000.00, '2024-08-14 15:16:48.000000', 'ACTIVATED', 1, NULL, 9),
('COUR', '2312345678', 1000000.00, '2024-08-15 19:18:08.000000', 'ACTIVATED', 0, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `compter`
--

CREATE TABLE `compter` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `compter`
--

INSERT INTO `compter` (`id`) VALUES
(1000003);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `cin` varchar(255) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `place_of_birth` varchar(255) DEFAULT NULL,
  `sex` enum('F','M') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `cin`, `date_of_birth`, `firstname`, `name`, `nationality`, `place_of_birth`, `sex`, `email`) VALUES
(1, '07473114', '1996-06-13', 'Mohammed Ali', 'KLAY', 'American', 'Virginia', 'M', 'klay@example.com'),
(2, '07473156', '1991-09-19', 'Doe', 'Johe', 'AMERICAN', 'NY', 'M', 'johe.doe@example.com'),
(3, '07473157', '1991-09-19', 'Garden', 'Eva', 'AMERICAN', 'NY', 'F', 'garden@example.com'),
(4, '09234567', '1985-12-25', 'Smith', 'Jane', 'AMERICAN', 'Los Angeles', 'F', 'jane.smith@example.com'),
(5, '12345678', '1990-04-10', 'Johnson', 'Alex', 'CANADIAN', 'Toronto', 'M', 'alex.johnson@example.com'),
(6, '87654321', '1985-08-15', 'Smith', 'Emily', 'BRITISH', 'London', 'F', 'emily.smith@example.com'),
(7, '34567890', '1982-07-22', 'Brown', 'Michael', 'AUSTRALIAN', 'Sydney', 'M', 'michael.brown@example.com'),
(8, '98765432', '1989-06-06', 'Sophia', 'Jones', 'AMERICAN', 'San Francisco', 'F', 'sophia.jones@example.com'),
(9, '07473154', '2000-02-01', 'Mariem', 'Mariem', 'Tunisienne', 'Tunis', 'F', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `password`, `role`, `username`) VALUES
(1, '$2a$10$UNFRFcMufVECjtYpZVL12OZ2hJpGfa5p6OOSBq4UC.6fa/hUbAGBG', 'ADMIN', 'yassine');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_operation`
--
ALTER TABLE `account_operation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmimm8esca3p6ux3s6bdwvu1cn` (`bank_account_id`);

--
-- Indexes for table `bank_account`
--
ALTER TABLE `bank_account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj818ht4ban0c4uw4bmsbf3jme` (`customer_id`);

--
-- Indexes for table `compter`
--
ALTER TABLE `compter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5i9set5rxqmiqx9aq5i6jh6ba` (`cin`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_operation`
--
ALTER TABLE `account_operation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account_operation`
--
ALTER TABLE `account_operation`
  ADD CONSTRAINT `FKmimm8esca3p6ux3s6bdwvu1cn` FOREIGN KEY (`bank_account_id`) REFERENCES `bank_account` (`id`);

--
-- Constraints for table `bank_account`
--
ALTER TABLE `bank_account`
  ADD CONSTRAINT `FKj818ht4ban0c4uw4bmsbf3jme` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
