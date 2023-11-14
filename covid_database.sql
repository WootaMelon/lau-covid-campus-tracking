-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 07, 2021 at 01:50 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `covid_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `profiles`
--

CREATE TABLE `profiles` (
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `full_name` text NOT NULL,
  `email` text NOT NULL,
  `photo` text NOT NULL,
  `vacc_status` text NOT NULL,
  `vacc_cert` text DEFAULT NULL,
  `pcr_test` text DEFAULT NULL,
  `status_condition` text NOT NULL DEFAULT 'safe',
  `quarantine_days` text DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `profiles`
--

INSERT INTO `profiles` (`username`, `password`, `full_name`, `email`, `photo`, `vacc_status`, `vacc_cert`, `pcr_test`, `status_condition`, `quarantine_days`, `location`) VALUES
('BananaJoe', '52842524470e604f3391605f2e196939d1e785cc500d8b79daab79a5ca2a58e3', 'Joe', 'Joebanana@gmail.com', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'negative', 'at risk', '2021-12-06', 'Irwin Hall'),
('BOB', 'ead15a79ca74c723065edb1a83ac8c795cd93784c42dc939f1c876168cb1fca2', 'Youssef', 'BOB@LOL.COM', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'positive', 'contagious', '2021-12-06', 'Irwin Hall'),
('Mehdi123', '7b9a0991144b533887766dbd3cd8ceba12c2957438594ec43efe422c87943293', 'Mehdi', 'Mehdi123@gmail.com', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'negative', 'at risk', '2021-12-07', 'Irwin Hall'),
('user1', '8a35e0ef6b995a3af7a084c7b39f3f9aa96f4ce6b961d27d5ee084a779b93ec3', 'user1', 'user1@email.com', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'negative', 'at risk', NULL, 'Sage Hall'),
('user2', '8a35e0ef6b995a3af7a084c7b39f3f9aa96f4ce6b961d27d5ee084a779b93ec3', 'user2', 'user2@email.com', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'positive', 'contagious', '2021-12-07', 'Sage Hall'),
('user3', '8a35e0ef6b995a3af7a084c7b39f3f9aa96f4ce6b961d27d5ee084a779b93ec3', 'user3', 'user3@email.com', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'negative', 'at risk', NULL, 'Irwin Hall'),
('Woota', 'f5fbc6fe84c365315f491d4275c2f2e5d3c60f25684e1d62e7e9fe63abf8d0d8', 'Mohamad', 'WOOTA@MELON.COM', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'Vaccinated', '/Users/mohamadalawieh/Downloads/1dude.jpg', 'negative', 'at risk', NULL, 'Irwin Hall');

-- --------------------------------------------------------

--
-- Table structure for table `trusting`
--

CREATE TABLE `trusting` (
  `user_name` varchar(100) NOT NULL,
  `trusted_username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trusting`
--

INSERT INTO `trusting` (`user_name`, `trusted_username`) VALUES
('BOB', 'Woota'),
('BOB', 'Woota'),
('BananaJoe', 'BOB'),
('BOB', 'Woota'),
('Mehdi123', 'Woota'),
('BOB', 'Mehdi123'),
('user2', 'user1'),
('user2', 'user1'),
('user1', 'user2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `profiles`
--
ALTER TABLE `profiles`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `trusting`
--
ALTER TABLE `trusting`
  ADD KEY `user_name` (`user_name`),
  ADD KEY `trusted_username` (`trusted_username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `trusting`
--
ALTER TABLE `trusting`
  ADD CONSTRAINT `trusting_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `profiles` (`username`),
  ADD CONSTRAINT `trusting_ibfk_2` FOREIGN KEY (`trusted_username`) REFERENCES `profiles` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
