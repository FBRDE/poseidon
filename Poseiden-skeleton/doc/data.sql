CREATE DATABASE  IF NOT EXISTS `demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `demo`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `bidlist`
--

DROP TABLE IF EXISTS `bidlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bidlist` (
  `bid_list_id` tinyint NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `bid_quantity` double DEFAULT NULL,
  `ask_quantity` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `bid_list_date` timestamp NULL DEFAULT NULL,
  `commentary` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `source_list_id` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`bid_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidlist`
--

LOCK TABLES `bidlist` WRITE;
/*!40000 ALTER TABLE `bidlist` DISABLE KEYS */;
INSERT INTO `bidlist` VALUES (13,'Account 1','Type1',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,'Account2','Type2',5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,'Account3','Type3',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'Account 4','Type 4',9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,'Account 5','Type 5',9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bidlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curvepoint`
--

DROP TABLE IF EXISTS `curvepoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curvepoint` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `curve_id` tinyint DEFAULT NULL,
  `as_of_date` timestamp NULL DEFAULT NULL,
  `term` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curvepoint`
--

LOCK TABLES `curvepoint` WRITE;
/*!40000 ALTER TABLE `curvepoint` DISABLE KEYS */;
INSERT INTO `curvepoint` VALUES (8,1,NULL,20,30,NULL),(9,2,NULL,8,9,NULL),(10,3,NULL,20,5,NULL),(11,4,NULL,1,2,NULL),(20,6,NULL,7,8,NULL);
/*!40000 ALTER TABLE `curvepoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (49);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `moodys_rating` varchar(125) DEFAULT NULL,
  `sand_p_rating` varchar(125) DEFAULT NULL,
  `fitch_rating` varchar(125) DEFAULT NULL,
  `order_number` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (9,'Moody Rating1','SandP Rating1','Fitch Rating',1),(17,'Moody Rating2','SandP Rating2','Fitch Rating2',2);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rulename`
--

DROP TABLE IF EXISTS `rulename`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rulename` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(125) DEFAULT NULL,
  `description` varchar(125) DEFAULT NULL,
  `json` varchar(125) DEFAULT NULL,
  `template` varchar(512) DEFAULT NULL,
  `sql_str` varchar(125) DEFAULT NULL,
  `sql_part` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rulename`
--

LOCK TABLES `rulename` WRITE;
/*!40000 ALTER TABLE `rulename` DISABLE KEYS */;
INSERT INTO `rulename` VALUES (1,'Name 1','Description 1','Json 1','Template 1','sqlStr 1','Sql part 1'),(13,'Name 2','Description 2','Json 2','Template 2','sqlStr 2','Sql part 2'),(16,'Name 10 edit','Description 10 edi','Json 10 edit','Template 10 edit','sqlStr 10 edit','Sql part 10 edit');
/*!40000 ALTER TABLE `rulename` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade` (
  `trade_id` tinyint NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `buy_quantity` double DEFAULT NULL,
  `sell_quantity` double DEFAULT NULL,
  `buy_price` double DEFAULT NULL,
  `sell_price` double DEFAULT NULL,
  `trade_date` timestamp NULL DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `source_list_id` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (2,'Account1','Type1',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Account2','Type2',15,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'Account3','Type3',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `username` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `role` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'faty','$2a$10$787A0565hPPxvlCl9je32.hB.1opI8Qn5gc1PBpPZM.NmlEmF.//q','faty','USER'),(19,'user','$2a$10$onRcJfZX.eEdN7akGFr2XOJlZ7eSFgA2vU5uR3OF89L.AyfgUoGlq','user','USER'),(20,'admin','$2a$10$Bt98XA60SFElf7dEQfTzGu58CIV2Vr452FoDeGf59eFeQHvstqJlC','admin','ADMIN'),(23,'user4','$2a$10$9R1e1ILoC0cPvAollTycVOXKQbLilhFzM.6tsooDoAhCzl3j0ZCAG','user4','ADMIN'),(24,'admin2','$2a$10$aQxqHNntSkui3cN6R.LBgus5gZOXU8yFk578/5XRFBdkhc4qSL2nO','admin2','ADMIN'),(25,'user3','$2a$10$Is/Yn10TYhJtfvmVwwXnF.weNshg3EdthXMXR.Vxnerv293RGIXvO','user3','ADMIN'),(26,'user2','$2a$10$hctyd1HQRNLW/BUCzKQ0V.A0Nu31WhlliQY8jwzA1eV8A89Vbh.Nm','user2','ADMIN'),(36,'meme','$2a$10$Pbc6EBgJDFKWOOvL6MvdG.tpAA/ASbip5bKXVqWZ.nBnWTzr2tZam','meme','ADMIN'),(45,'user10edit','$2a$10$OttERZHcM/VpSF59.NtLsew98E9MEKuBo9htnKfNViPeomWuGaT1e','fullName10edit','ADMIN'),(48,'moncompte','$2a$10$X8yPyV.KZgcke9ByXZ4e/eF7Ch9wUY2zvSUWYK/6Wx1aIyTzp.Nx2',NULL,'USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'demo'
--

--
-- Dumping routines for database 'demo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-14 17:03:44
