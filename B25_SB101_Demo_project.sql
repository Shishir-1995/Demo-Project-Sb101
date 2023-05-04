-- MySQL dump 10.13  Distrib 5.7.14, for Win64 (x86_64)
--
-- Host: localhost    Database: B25_SB101_Demo_project
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `age_band`
--

DROP TABLE IF EXISTS `age_band`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `age_band` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_age` int(11) NOT NULL,
  `start_age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `age_band`
--

LOCK TABLES `age_band` WRITE;
/*!40000 ALTER TABLE `age_band` DISABLE KEYS */;
INSERT INTO `age_band` VALUES (1,35,18),(2,50,36),(3,70,51);
/*!40000 ALTER TABLE `age_band` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `age_band_wise_premium_for_plan`
--

DROP TABLE IF EXISTS `age_band_wise_premium_for_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `age_band_wise_premium_for_plan` (
  `premium_amount` double NOT NULL,
  `surcharge_amount` double NOT NULL,
  `plan_id` int(11) NOT NULL,
  `ageBand_id` int(11) NOT NULL,
  PRIMARY KEY (`ageBand_id`,`plan_id`),
  KEY `FK7ndbygfyemaqpddsfj4ftfh7i` (`plan_id`),
  CONSTRAINT `FK6wwy5daxkfia67lp2j4lwto4l` FOREIGN KEY (`ageBand_id`) REFERENCES `age_band` (`id`),
  CONSTRAINT `FK7ndbygfyemaqpddsfj4ftfh7i` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `age_band_wise_premium_for_plan`
--

LOCK TABLES `age_band_wise_premium_for_plan` WRITE;
/*!40000 ALTER TABLE `age_band_wise_premium_for_plan` DISABLE KEYS */;
INSERT INTO `age_band_wise_premium_for_plan` VALUES (19000,5000,8,1),(11000,2000,9,1),(9000,1000,10,1),(25000,6000,8,2),(13000,3000,9,2),(11000,2000,10,2),(30000,7000,8,3),(9000,1000,9,3),(13000,3000,10,3);
/*!40000 ALTER TABLE `age_band_wise_premium_for_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(50) NOT NULL,
  `estd_year` int(11) NOT NULL,
  `sector_type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hvj5mm9gg8ca6gky5uxevqx0c` (`company_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (8,'LIC',1956,'private'),(9,'Max',1995,'private'),(10,'HDFC',1985,'private');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_of_birth` date NOT NULL,
  `has_medical_condition` int(11) NOT NULL,
  `is_deleted` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mufchskagt7e1w4ksmt9lum5l` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'1985-05-03',1,0,'ab','ab','ab'),(2,'2001-01-01',0,0,'CD','cd','cd');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_of_purchase` date NOT NULL,
  `expiration_date` date NOT NULL,
  `policy_status` varchar(20) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlxvrjb6oyfjjffp0vvusn22uy` (`customer_id`),
  KEY `FKj1htofqgbuqghagwfde26ukmx` (`plan_id`),
  CONSTRAINT `FKj1htofqgbuqghagwfde26ukmx` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`),
  CONSTRAINT `FKlxvrjb6oyfjjffp0vvusn22uy` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (6,'2023-05-04','2024-05-03','continued',1,8),(7,'2022-05-04','2024-05-04','continued',1,9),(8,'2022-05-05','2023-05-04','continued',1,10),(9,'2022-04-01','2023-03-31','discontinued',1,9);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gst_rate` double NOT NULL,
  `max_coverage_age` int(11) NOT NULL,
  `plan_name` varchar(50) NOT NULL,
  `plan_type` varchar(10) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1vwsvc1p74fvdb96vjjx0gypw` (`company_id`),
  CONSTRAINT `FK1vwsvc1p74fvdb96vjjx0gypw` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (8,18,70,'TechTerm','Term',8),(9,18,70,'Optima','Health',10),(10,18,70,'MaxLifeSecure','Term',9);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premium_payment`
--

DROP TABLE IF EXISTS `premium_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `premium_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `mode_of_payment` varchar(255) NOT NULL,
  `payment_date` date NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1m0f56tvwafqdf7anle7dxtod` (`order_id`),
  CONSTRAINT `FK1m0f56tvwafqdf7anle7dxtod` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premium_payment`
--

LOCK TABLES `premium_payment` WRITE;
/*!40000 ALTER TABLE `premium_payment` DISABLE KEYS */;
INSERT INTO `premium_payment` VALUES (1,28320,'cash','2023-05-04',6),(2,15340,'online','2023-05-03',7),(3,10620,'online','2022-05-05',8),(4,12980,'cash','2023-03-31',9),(5,18880,'cheque','2023-05-04',7);
/*!40000 ALTER TABLE `premium_payment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-04 21:05:00
