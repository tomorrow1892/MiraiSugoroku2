-- MySQL dump 10.18  Distrib 10.3.27-MariaDB, for Linux (x86_64)
--
-- Host: 192.168.0.21    Database: sugoroku
-- ------------------------------------------------------
-- Server version	5.7.31-log

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `board_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sequence` int(11) NOT NULL,
  `square_id` bigint(20) DEFAULT NULL,
  `sugoroku_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,1,2,4),(2,2,6,4),(3,3,1,4),(4,4,4,4),(5,5,3,4),(6,6,7,4),(7,7,8,4),(8,1,5,5),(9,2,8,5),(10,3,1,5),(11,4,4,5),(12,5,3,5),(13,6,2,5),(14,7,7,5);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_approved` bit(1) NOT NULL,
  `limit_date` date DEFAULT NULL,
  `n_groups` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'','2022-10-31',5,'公民未来イベント1','2022-09-22');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `player_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `is_break` bit(1) DEFAULT NULL,
  `is_goaled` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `sugoroku_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'cat','\0','\0','Tomoro Nakahashi',1,0,0,1),(2,'dog','\0','\0','Tomoro Nakahashi',2,0,0,1),(3,'dog','\0','\0','Tomoro Nakahashi',1,0,0,2),(4,'cat','\0','\0','Tomoro Nakahashi',2,0,0,2),(5,'dog','\0','\0','Tomoro Nakahashi',1,0,0,3),(6,'cat','\0','\0','Tomoro Nakahashi',2,0,0,3),(7,'dog','\0','\0','Tomoro Nakahashi',1,0,0,4),(8,'cat','\0','\0','Tomoro Nakahashi',2,0,0,4),(9,'dog','\0','\0','Tomoro Nakahashi',1,0,0,5),(10,'hamster','\0','\0','Tomoro Nakahashi',2,0,0,5);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `square`
--

DROP TABLE IF EXISTS `square`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `square` (
  `square_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creator_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `is_approved` bit(1) NOT NULL,
  `square_effect` int(11) NOT NULL,
  `square_event_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `picture` longtext,
  PRIMARY KEY (`square_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `square`
--

LOCK TABLES `square` WRITE;
/*!40000 ALTER TABLE `square` DISABLE KEYS */;
INSERT INTO `square` VALUES (1,1,'三田市にはフルーツショップがいっぱいあります',1,2,'',1,1,'フルーツ狩りを楽しんだ!',''),(2,1,'ダム周辺ではBBQを楽しめる公園などがあります',1,2,'',-1,7,'青野ダムでボートに乗ったが転覆した',''),(3,1,'花のじゅうたんという公園が有名です',1,2,'',1,2,'花畑に行った',''),(4,1,'三田市母子にある滝',1,2,'',1,1,'尼ン滝で納涼',''),(5,1,'三田のバスの本数は少ないです',1,2,'',-1,8,'バスに乗り遅れた',''),(6,1,'坂が多い',1,2,'',-1,9,'坂で転ぶ',''),(7,1,'三田の冬は寒いです',1,2,'',-1,8,'風邪を引いた',''),(8,1,'三田は商業施設が充実してます',1,2,'',1,2,'アウトレットモールで買い物をする','');
/*!40000 ALTER TABLE `square` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `square_creator`
--

DROP TABLE IF EXISTS `square_creator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `square_creator` (
  `creator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_id` bigint(20) DEFAULT NULL,
  `group` int(11) NOT NULL,
  `is_permitted` bit(1) NOT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `square_creator`
--

LOCK TABLES `square_creator` WRITE;
/*!40000 ALTER TABLE `square_creator` DISABLE KEYS */;
INSERT INTO `square_creator` VALUES (1,1,2,'','tomorrow','中橋友郎','トモロー'),(2,1,1,'','taro','三田太郎','さんだろう');
/*!40000 ALTER TABLE `square_creator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `square_event`
--

DROP TABLE IF EXISTS `square_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `square_event` (
  `square_eventid` bigint(20) NOT NULL,
  `action_number` int(11) NOT NULL,
  `argument` int(11) NOT NULL,
  `event_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`square_eventid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `square_event`
--

LOCK TABLES `square_event` WRITE;
/*!40000 ALTER TABLE `square_event` DISABLE KEYS */;
INSERT INTO `square_event` VALUES (1,1,1,'1進む'),(2,1,2,'2進む'),(3,1,3,'3進む'),(4,1,4,'4進む'),(5,1,5,'5進む'),(6,1,6,'6進む'),(7,1,-1,'1戻る'),(8,1,-2,'2戻る'),(9,1,-3,'3戻る'),(10,1,-4,'4戻る'),(11,1,-5,'5戻る'),(12,1,-6,'6戻る'),(13,2,0,'一回休み'),(14,0,0,'');
/*!40000 ALTER TABLE `square_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugoroku`
--

DROP TABLE IF EXISTS `sugoroku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugoroku` (
  `sugoroku_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `length` int(11) NOT NULL,
  `n_players` int(11) NOT NULL,
  `now_player` int(11) NOT NULL,
  PRIMARY KEY (`sugoroku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugoroku`
--

LOCK TABLES `sugoroku` WRITE;
/*!40000 ALTER TABLE `sugoroku` DISABLE KEYS */;
INSERT INTO `sugoroku` VALUES (1,7,2,1),(2,7,2,1),(3,7,2,1),(4,7,2,1),(5,7,2,1);
/*!40000 ALTER TABLE `sugoroku` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-22 13:21:09
