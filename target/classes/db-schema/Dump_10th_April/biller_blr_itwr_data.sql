-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: biller
-- ------------------------------------------------------
-- Server version	8.0.1-dmr-log

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
-- Table structure for table `blr_itwr_data`
--

DROP TABLE IF EXISTS `blr_itwr_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blr_itwr_data` (
  `req_no` varchar(10) NOT NULL,
  `req_title` varchar(150) DEFAULT NULL,
  `coo_intake_no` varchar(10) NOT NULL,
  `it_sme` varchar(100) DEFAULT NULL,
  `bus_area` varchar(50) DEFAULT NULL,
  `work_type` varchar(50) DEFAULT NULL,
  `demand_type` varchar(50) DEFAULT NULL,
  `fund_type` varchar(20) DEFAULT NULL,
  `cost_center` varchar(25) DEFAULT NULL,
  `vendor_class` varchar(50) DEFAULT NULL,
  `asm` varchar(100) DEFAULT NULL,
  `primary_vendor` varchar(15) DEFAULT NULL,
  `asd` varchar(100) DEFAULT NULL,
  `overall_status` varchar(30) DEFAULT NULL,
  `vendor_est_hours` varchar(5) DEFAULT NULL,
  `vendor_actual_hours` varchar(5) DEFAULT NULL,
  `pcr_est_hours` varchar(5) DEFAULT NULL,
  `pcr_actual_hours` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blr_itwr_data`
--

LOCK TABLES `blr_itwr_data` WRITE;
/*!40000 ALTER TABLE `blr_itwr_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `blr_itwr_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 16:03:26
