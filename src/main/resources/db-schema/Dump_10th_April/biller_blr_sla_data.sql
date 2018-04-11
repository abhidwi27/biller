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
-- Table structure for table `blr_sla_data`
--

DROP TABLE IF EXISTS `blr_sla_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blr_sla_data` (
  `seq_id` smallint(5) NOT NULL AUTO_INCREMENT,
  `weekend_date` varchar(100) DEFAULT 'NA',
  `asm` varchar(100) DEFAULT 'NA',
  `asd` varchar(100) DEFAULT 'NA',
  `asm_itwr` varchar(100) DEFAULT 'NA',
  `asd_itwr` varchar(100) DEFAULT 'NA',
  `emp_id` varchar(10) NOT NULL,
  `emp_name` varchar(100) DEFAULT 'NA',
  `activity` varchar(100) DEFAULT 'NA',
  `work_item` varchar(10) NOT NULL,
  `on_off_shore` varchar(10) DEFAULT 'NA',
  `total_hours` decimal(3,1) DEFAULT '0.0',
  `shift_detail` varchar(10) DEFAULT 'NA',
  `category` varchar(50) DEFAULT 'NA',
  `bill_type` varchar(50) DEFAULT 'NA',
  `dm` varchar(100) DEFAULT 'NA',
  `app_area` varchar(50) DEFAULT 'NA',
  `business_area` varchar(50) DEFAULT 'NA',
  `tower` varchar(20) DEFAULT 'NA',
  `bam` varchar(100) DEFAULT 'NA',
  `remarks` varchar(100) DEFAULT 'NA',
  `is_billable` varchar(3) DEFAULT 'NA',
  `wr_no` varchar(20) DEFAULT 'NA',
  `planned_hours` smallint(3) NOT NULL DEFAULT '0',
  `comments` varchar(100) DEFAULT 'NA',
  `wr_desc` varchar(150) DEFAULT 'NA',
  `cost_center` varchar(25) DEFAULT 'NA',
  `fund_type` varchar(20) DEFAULT 'NA',
  `vendor_class` varchar(100) DEFAULT 'NA',
  `account_id` varchar(10) DEFAULT 'NA',
  `bill_cycle` varchar(10) DEFAULT 'NA',
  `change_log` varchar(200) DEFAULT 'NA',
  `active` tinyint(4) DEFAULT '1',
  `modified_by` varchar(50) DEFAULT 'NA',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blr_sla_data`
--

LOCK TABLES `blr_sla_data` WRITE;
/*!40000 ALTER TABLE `blr_sla_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `blr_sla_data` ENABLE KEYS */;
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
