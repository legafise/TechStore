-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: techstore
-- ------------------------------------------------------
-- Server version	5.7.28-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `baskets`
--

DROP TABLE IF EXISTS `baskets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `baskets` (
                           `user_id` int(11) NOT NULL,
                           `good_id` int(11) NOT NULL,
                           `quantity` smallint(11) NOT NULL,
                           UNIQUE KEY `user_id` (`user_id`,`good_id`),
                           KEY `baskets_goods_idx` (`good_id`),
                           CONSTRAINT `baskets_goods` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                           CONSTRAINT `baskets_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(60) NOT NULL,
                         `price` decimal(10,2) NOT NULL,
                         `type_id` smallint(11) NOT NULL,
                         `description` varchar(200) NOT NULL,
                         `picture` varchar(45) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`),
                         KEY `goods_goods_types_idx` (`type_id`),
                         CONSTRAINT `goods_goods_types` FOREIGN KEY (`type_id`) REFERENCES `goods_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_reviews`
--

DROP TABLE IF EXISTS `goods_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods_reviews` (
                                 `review_id` int(11) NOT NULL,
                                 `good_id` int(11) NOT NULL,
                                 UNIQUE KEY `review_id` (`review_id`,`good_id`),
                                 KEY `goods_reviews_goods_idx` (`good_id`),
                                 CONSTRAINT `goods_reviews_goods` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                 CONSTRAINT `goods_reviews_reviews` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_types`
--

DROP TABLE IF EXISTS `goods_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods_types` (
                               `id` smallint(11) NOT NULL AUTO_INCREMENT,
                               `name` varchar(20) COLLATE utf8_bin NOT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordered_goods`
--

DROP TABLE IF EXISTS `ordered_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_goods` (
                                 `order_id` int(11) NOT NULL,
                                 `good_id` int(11) NOT NULL,
                                 `quantity` smallint(11) NOT NULL,
                                 UNIQUE KEY `good_id` (`good_id`,`order_id`),
                                 KEY `ordered_goods_orders_idx` (`order_id`),
                                 CONSTRAINT `ordered_goods` FOREIGN KEY (`good_id`) REFERENCES `goods` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                 CONSTRAINT `ordered_goods_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `status` varchar(30) NOT NULL,
                          `price` decimal(10,2) NOT NULL,
                          `user_id` int(11) NOT NULL,
                          `address` varchar(45) NOT NULL,
                          `date` date NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `orders_users_idx` (`user_id`),
                          CONSTRAINT `orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `rate` float NOT NULL,
                           `content` varchar(200) COLLATE utf8_bin NOT NULL,
                           `user_id` int(11) NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `id_UNIQUE` (`id`),
                           KEY `reviews_users_idx` (`user_id`),
                           CONSTRAINT `reviews_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(30) NOT NULL,
                         `surname` varchar(30) NOT NULL,
                         `login` varchar(45) NOT NULL,
                         `password` varchar(60) NOT NULL,
                         `email` varchar(45) NOT NULL,
                         `profile_picture` varchar(45) NOT NULL,
                         `birth_date` date NOT NULL,
                         `balance` decimal(10,0) NOT NULL,
                         `role` smallint(11) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-20 15:51:22

