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
-- Table structure for table `blr_user`
--

DROP TABLE IF EXISTS `blr_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blr_user` (
  `user_id` varchar(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `role_id` smallint(2) NOT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `delegate_id` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blr_user`
--

LOCK TABLES `blr_user` WRITE;
/*!40000 ALTER TABLE `blr_user` DISABLE KEYS */;
INSERT INTO `blr_user` VALUES ('adwivedi','Abhishek','','Dwivedi',2,'adwived8@in.ibm.com','test1234',NULL,'2017-11-27 15:08:53'),('vveera','Vivek','K','Veera',8,'vivveera@in.ibm.com','test1234',NULL,'2017-08-28 17:46:12'),('ssbram','Sudharshini','','Subramanian',2,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('gps','Grisha','P','S',2,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('spadhi','Sonali','S','Padhi',2,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('mghosh','Madhurima','','Ghosh',3,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('cmandala','Chandana','M','Guttikinda',3,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('rkumar','Ranjith','','Kumar',3,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('dchhipa','Disha','','Chhipa',1,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('jpai','Jyothi','','Pai',4,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02'),('prabha','Prabah','','Kumar',4,'adwived8@in.ibm.com','test1234',NULL,'2017-12-05 18:32:02');
/*!40000 ALTER TABLE `blr_user` ENABLE KEYS */;
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
