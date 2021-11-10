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
-- Dumping data for table `baskets`
--

LOCK TABLES `baskets` WRITE;
/*!40000 ALTER TABLE `baskets` DISABLE KEYS */;
INSERT INTO `baskets` VALUES (2,1,1),(5,2,1),(5,6,1),(6,1,2);
/*!40000 ALTER TABLE `baskets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'Iphone 13',3144.23,1,'Expensive but cool thing','iphone13.jpg'),(2,'Lenovo Ideapad 3',3506.34,3,'Laptop for work','Ideapad2.jpg'),(6,'Poco X3 PRO',439.43,1,'Best chois for the money','default.jpg');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `goods_reviews`
--

LOCK TABLES `goods_reviews` WRITE;
/*!40000 ALTER TABLE `goods_reviews` DISABLE KEYS */;
INSERT INTO `goods_reviews` VALUES (1,1),(5,1),(2,2),(4,6);
/*!40000 ALTER TABLE `goods_reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ordered_goods`
--

LOCK TABLES `ordered_goods` WRITE;
/*!40000 ALTER TABLE `ordered_goods` DISABLE KEYS */;
INSERT INTO `ordered_goods` VALUES (4,1,1),(5,1,1),(5,2,1),(6,6,1);
/*!40000 ALTER TABLE `ordered_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (4,'The order is completed',3144.23,2,'Minsk, Korolenki 23, 15','2021-10-29'),(5,'The order is completed',6650.57,6,'Minsk, Volgogradskaya 15, 4','2021-11-01'),(6,'The order is being processed',439.43,2,'Minsk, Korolenki 23, 15','2021-11-03');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,4.5,'So good',2),(2,4.8,'Best laptop!',4),(4,4.2,'Coll item',4),(5,4,'Ok, but it could have been better for the money',5);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Roman','Lash','legafise','12340987','tapoc04@yandex.ru','default.jpg','2004-02-12',126,3),(5,'Vasya','Pupkin','PupVasi','12345678','vasyapup@yandex.ru','default.jpg','2003-05-16',65,3),(6,'Gleb','Kirienko','SirPakun','zim','floppa@mail.ru','default.jpg','2003-07-31',243,3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-10 14:12:12
