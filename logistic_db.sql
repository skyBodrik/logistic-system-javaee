-- phpMyAdmin SQL Dump
-- version 4.4.14.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 20, 2017 at 08:22 PM
-- Server version: 5.5.49-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `logistic_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `calendar`
--

CREATE TABLE IF NOT EXISTS `calendar` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `calendar`
--

INSERT INTO `calendar` (`id`, `date`, `user_id`, `city_id`) VALUES
(1, '2016-11-26', 3, 1),
(2, '2016-11-28', 3, 2),
(3, '2016-11-30', 3, 1),
(5, '2016-12-03', 3, 2),
(6, '2016-12-02', 3, 1),
(7, '2016-12-10', 3, 2),
(8, '2016-12-13', 3, 1),
(9, '2016-12-27', 3, 1),
(10, '2016-12-24', 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id`, `name`) VALUES
(1, 'Красноярск'),
(2, 'Санкт-Петербург');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL,
  `weight` double NOT NULL,
  `width` double NOT NULL,
  `height` double NOT NULL,
  `length` double NOT NULL,
  `from_city_id` int(11) NOT NULL,
  `from_address` varchar(512) NOT NULL,
  `to_city_id` int(11) NOT NULL,
  `to_address` varchar(512) NOT NULL,
  `recipient_name` varchar(128) NOT NULL,
  `recipient_phone` varchar(128) NOT NULL,
  `cost` double NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `client_id` int(11) NOT NULL,
  `carrier_id` int(11) NOT NULL,
  `date_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `weight`, `width`, `height`, `length`, `from_city_id`, `from_address`, `to_city_id`, `to_address`, `recipient_name`, `recipient_phone`, `cost`, `status`, `client_id`, `carrier_id`, `date_create`) VALUES
(1, 0.8, 0.1, 0.05, 0.5, 1, 'ул. Робеспьера', 2, 'пр-кт Энгельса', 'Бродт Игорь Иванович', '+7 951 666 04 02', 1500, 3, 2, 3, '2016-11-24 23:45:53'),
(2, 0.8, 0.1, 0.05, 0.5, 2, 'пр-кт Стачек', 1, 'ул. Молокова 40', 'Бродт Игорь Иванович', '+7 951 666 04 02', 0, 3, 2, 6, '2016-11-24 23:47:06'),
(3, 0.8, 0.1, 0.05, 0.5, 2, 'ул. Робеспьера', 2, 'пр-кт Энгельса', 'Бродт Игорь Иванович', '+7 951 666 04 02', 0.5, 1, 2, 3, '2016-12-23 00:33:54');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  `name` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(512) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `maxweight` double NOT NULL DEFAULT '0',
  `width` double NOT NULL DEFAULT '0',
  `height` double NOT NULL DEFAULT '0',
  `length` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `type`, `name`, `email`, `password`, `phone`, `maxweight`, `width`, `height`, `length`) VALUES
(1, 1, 'Операторов Оператор', 'operator@gmail.com', '698d51a19d8a121ce581499d7b701668', 'test', 0, 0, 0, 0),
(2, 2, 'Заказчиков Заказчик 2', 'client@gmail.com', '698d51a19d8a121ce581499d7b701668', '7-88-25', 0, 0, 0, 0),
(3, 3, 'Перевозиков Перевозчик', 'carrier@gmail.com', '698d51a19d8a121ce581499d7b701668', '+7 950 410 5690', 1050, 2.5, 3, 6),
(4, 2, 'hello', 'hello', 'hello', 'hello', 0, 0, 0, 0),
(6, 3, 'Перевозчик 1', 'perevozchik-one@rambler.ru', 'password', '', 25000, 3, 4, 8),
(7, 3, 'Петров Василий', 'pertov@mail.com', '111', '+7 88 25', 0, 0, 0, 0),
(8, 2, 'Путин В.В.', 'putin@kremlin.ru', '698d51a19d8a121ce581499d7b701668', '+ 7 777 777 77 77', 0, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `calendar`
--
ALTER TABLE `calendar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `extra` (`date`,`city_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `city_id` (`city_id`);

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `carrier_id` (`carrier_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `calendar`
--
ALTER TABLE `calendar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `cities`
--
ALTER TABLE `cities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `calendar`
--
ALTER TABLE `calendar`
  ADD CONSTRAINT `calendar_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
  ADD CONSTRAINT `calendar_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`carrier_id`) REFERENCES `users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
