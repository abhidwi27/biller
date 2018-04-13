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
-- Table structure for table `blr_data_header`
--

DROP TABLE IF EXISTS `blr_data_header`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blr_data_header` (
  `header_id` smallint(5) DEFAULT NULL,
  `header_desc` varchar(100) NOT NULL,
  `ilc_order` smallint(5) DEFAULT NULL,
  `sla_order` smallint(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blr_data_header`
--

LOCK TABLES `blr_data_header` WRITE;
/*!40000 ALTER TABLE `blr_data_header` DISABLE KEYS */;
INSERT INTO `blr_data_header` VALUES (1,'Select',1,1),(2,'Emp ID',2,7),(3,'Name',3,8),(4,'Claim Code',4,10),(5,'Activity',5,9),(6,'Week End',6,2),(7,'Total Hrs',7,12),(8,'Shift',8,13),(9,'US/INDIA',9,0),(10,'Location',10,11),(11,'Billing',11,15),(12,'Category',12,14),(13,'BAM',13,20),(14,'App Aea',14,17),(15,'Business Area',15,18),(16,'Month',16,0),(17,'Quarter',17,0),(18,'DM',18,16),(19,'ASM',19,3),(20,'ASD',20,4),(21,'WR #',21,24),(22,'Is Ticket ?',22,0),(23,'Resource Type',23,0),(24,'CTC WR',24,0),(25,'RTC WR',25,0),(26,'Planned Hours',26,25),(27,'Billable?',27,22),(28,'Remarks',28,21),(29,'CTC/RTC',29,29),(30,'Work Type',30,0),(31,'WR Description',31,27),(32,'Cost Center',32,28),(33,'Category 2',33,0),(34,'OnOffLanded',34,0),(35,'Tower',35,19),(36,'Last Modified',36,0),(37,'ASM (ITWR)',37,5),(38,'ASD (ITWR)',38,6),(39,'ITWR Actuals',39,0),(40,'Group',40,0),(41,'Vendor Class',41,30),(42,'WR/INC/DEF',42,0),(43,'Action',0,0),(44,'Comments',0,26),(45,'Modified By',0,31),(46,'Account ID',43,32);
/*!40000 ALTER TABLE `blr_data_header` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 16:03:27
