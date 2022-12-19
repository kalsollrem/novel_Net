-- MariaDB dump 10.19  Distrib 10.9.2-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: novel
-- ------------------------------------------------------
-- Server version	10.9.2-MariaDB

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
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmark` (
  `bm_num` int(20) NOT NULL AUTO_INCREMENT,
  `u_num` int(20) NOT NULL,
  `n_num` int(20) NOT NULL,
  PRIMARY KEY (`bm_num`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
INSERT INTO `bookmark` VALUES
(3,2,1),
(19,0,1),
(20,1,2),
(21,2,3);
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hashtag` (
  `t_num` int(20) NOT NULL AUTO_INCREMENT,
  `h_tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `n_num` varchar(20) NOT NULL,
  `t_carte` varchar(4) DEFAULT 'none',
  PRIMARY KEY (`t_num`),
  KEY `n_num_idx` (`n_num`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` VALUES
(1,'#판타지','0','1st'),
(2,'#김정현','0','2st'),
(3,'#오재수','0','2st'),
(4,'#킹','4','2st'),
(5,'#공혁','4','2st'),
(6,'#기타','4','1st'),
(7,'#기타','4','2st'),
(8,'#기타','4','2st'),
(10,'#기타','4','2st'),
(18,'#오재수','2','2st'),
(19,'#킹','2','2st'),
(20,'#공혁','2','2st'),
(21,'#현대','2','1st'),
(22,'#김정현','2','2st'),
(23,'#오재수','2','2st'),
(24,'#킹','2','2st'),
(25,'#공혁','1','2st'),
(26,'#기타','1','1st'),
(27,'#테스트','1','2st'),
(28,'#테스트1','1','2st'),
(29,'#테스트2','1','2st'),
(30,'#테스트3','1','2st'),
(31,'#테스트','1','2st'),
(32,'#노동','1','2st'),
(33,'#헤이','1','2st'),
(34,'#홀리쉣버스트','1','2st'),
(35,'#홀리쉣버스트','0','2st'),
(36,'#홀리쉣버스트','0','2st'),
(37,'#홀버스트','0','2st'),
(38,'#홀버','0','2st'),
(51,'#로맨스','19','1st'),
(52,'#하나','19','2st'),
(53,'#둘','19','2st'),
(54,'#셋','19','2st'),
(55,'#넷','19','2st'),
(86,'#무협','10','1st'),
(87,'#김정현','10','2st'),
(88,'#오재수','10','2st'),
(89,'#킹','10','2st'),
(90,'#공혁','10','2st');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_good`
--

DROP TABLE IF EXISTS `m_good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_good` (
  `m_num` varchar(30) CHARACTER SET latin1 NOT NULL,
  `u_num` varchar(30) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`m_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_good`
--

LOCK TABLES `m_good` WRITE;
/*!40000 ALTER TABLE `m_good` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_good` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_record`
--

DROP TABLE IF EXISTS `m_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_record` (
  `mr_num` int(20) NOT NULL AUTO_INCREMENT,
  `n_num` int(20) NOT NULL,
  `m_num` int(20) NOT NULL,
  `u_num` int(20) NOT NULL,
  PRIMARY KEY (`mr_num`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_record`
--

LOCK TABLES `m_record` WRITE;
/*!40000 ALTER TABLE `m_record` DISABLE KEYS */;
INSERT INTO `m_record` VALUES
(1,1,44,15),
(2,2,44,1),
(3,2,35,15),
(4,2,35,2),
(5,2,44,15),
(64,2,40,19),
(65,2,40,19),
(66,2,41,19),
(67,2,40,19),
(68,2,40,19),
(69,2,32,19),
(70,2,35,19),
(71,2,35,19),
(72,2,32,19),
(73,2,33,19),
(74,2,34,19),
(75,2,35,19),
(76,2,35,19),
(77,2,36,19),
(78,2,38,19),
(79,2,38,19),
(80,2,38,19),
(81,2,39,19),
(82,2,37,19),
(83,2,32,19),
(84,2,35,19),
(85,2,35,19),
(86,2,34,19),
(87,2,34,19),
(88,2,36,19),
(89,2,36,19),
(90,2,35,19),
(91,2,35,19),
(92,2,35,19),
(93,2,36,19),
(94,2,35,19),
(95,2,35,19);
/*!40000 ALTER TABLE `m_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_warning`
--

DROP TABLE IF EXISTS `m_warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_warning` (
  `n_num` int(10) NOT NULL,
  `u_num` int(10) NOT NULL,
  `w_why` varchar(1000) DEFAULT NULL,
  `m_num` int(10) NOT NULL,
  PRIMARY KEY (`m_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_warning`
--

LOCK TABLES `m_warning` WRITE;
/*!40000 ALTER TABLE `m_warning` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_warning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES
(1,'spring1'),
(2,'spring2'),
(3,'test'),
(4,'김정현'),
(5,'1234'),
(6,'1'),
(7,'test'),
(8,'test'),
(9,'test'),
(10,'test'),
(11,'test'),
(12,'test'),
(13,'test'),
(14,'test'),
(15,'test'),
(16,'test'),
(17,'test'),
(18,'test'),
(19,'test'),
(20,'test'),
(21,'test'),
(22,'test'),
(23,'test'),
(24,'test'),
(25,'test'),
(26,'test'),
(27,'test'),
(28,'test'),
(29,'test'),
(30,'test'),
(31,'test'),
(32,'test'),
(33,'test'),
(34,'test'),
(35,'test'),
(36,'test'),
(37,'test'),
(38,'kalsollrem@naver.com'),
(39,'kalsollrem@naver.com'),
(40,'kalsollrem@naver.com'),
(41,'kalsollrem@naver.com'),
(42,'kalsollrem@naver.com'),
(43,'kalsollrem@naver.com'),
(44,'kalsollrem@naver.com'),
(45,'kalsollrem@naver.com'),
(46,'kalsollrem@naver.com'),
(47,'kalsollrem@naver.com'),
(48,'kalsollrem@naver.com'),
(49,'kalsollrem@naver.com'),
(50,'kalsollrem@naver.com'),
(51,'kalsollrem@gmail.com'),
(52,'kalsollrem@gmail.com'),
(53,'kalsollrem@gmail.com'),
(54,'kalsollrem@gmail.com'),
(55,'kalsollrem@gmail.com'),
(56,'kalsollrem@gmail.com'),
(57,'kalsollrem@gmail.com'),
(58,'kalsollrem@gmail.com'),
(59,'kalsollrem@gmail.com'),
(60,'kalsollrem@gmail.com'),
(61,'kalsollrem@gmail.com'),
(62,'kalsollrem@gmail.com'),
(63,'kalsollrem@gmail.com'),
(64,'kalsollrem@gmail.com'),
(65,'kalsollrem@gmail.com'),
(66,'kalsollrem@naver.com'),
(67,'kalsollrem@naver.com'),
(68,'kalsollrem@gmail.com'),
(69,'kalsollrem@gmail.com'),
(70,'kalsollrem@naver.com'),
(71,'kalsollrem@gmail.com');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memo`
--

DROP TABLE IF EXISTS `memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `memo` (
  `m_num` int(11) NOT NULL AUTO_INCREMENT,
  `n_num` varchar(30) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `m_title` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `m_memo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `m_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `m_count` int(11) NOT NULL DEFAULT 0,
  `m_good` int(11) NOT NULL DEFAULT 0,
  `b_stop` int(11) NOT NULL DEFAULT 0,
  `m_type` varchar(8) NOT NULL DEFAULT 'ep',
  PRIMARY KEY (`m_num`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memo`
--

LOCK TABLES `memo` WRITE;
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
INSERT INTO `memo` VALUES
(1,'1','a','b','2022-09-28 19:56:43',0,1,0,'gong'),
(2,'1','title','memoliset','2022-09-28 19:56:55',8,1,0,'ep'),
(3,'1','제목','내용','2022-09-28 19:59:04',0,1,0,'ep'),
(4,'1','제목','<p>내용</p>','2022-09-28 19:59:22',1,2,0,'ep'),
(5,'0','이동테스트','<p><span style=\"font-family: 돋움체;\">내용</span></p><p><span style=\"font-family: 굴림체;\"><b>입니다</b></span></p>','2022-09-28 20:03:04',0,0,0,'ep'),
(6,'0','제목','<p>내용</p>','2022-10-01 19:54:50',1,0,0,'ep'),
(8,'0','제목1','<p>내용2</p>','2022-10-18 07:18:30',1,0,0,'ep'),
(11,'0','공지글','<p>공지글 테스트</p>','2022-10-31 17:15:35',0,0,0,'gong'),
(12,'0','제목','<p>내용</p><p><img src=\"/noteImg/ab9bcc92-cb9b-4280-b50b-8a0d748898a0.jpg\" style=\"width: 905px;\"><br></p>','2022-10-31 18:20:34',0,0,0,'ep'),
(13,'0','1','<p>1<img src=\"/noteImg/ac4fc73c-061b-4ec9-8f92-de212be3adcb.jpg\" style=\"width: 992px;\"></p>','2022-10-31 18:21:49',0,0,0,'ep'),
(14,'0','1','<p>1</p>','2022-10-31 18:22:40',0,0,0,'ep'),
(17,'2','123','<p>123</p>','2022-10-31 18:41:39',1,0,0,'gong'),
(18,'1','a','b','2022-09-28 19:56:43',0,6,0,'gong'),
(19,'1','title','memoliset','2022-09-28 19:56:55',0,1,0,'ep'),
(20,'1','제목','내용','2022-09-28 19:59:04',0,1,0,'ep'),
(21,'0','제목','<p>내용</p>','2022-09-28 19:59:22',0,0,0,'ep'),
(22,'0','이동테스트','<p><span style=\"font-family: 돋움체;\">내용</span></p><p><span style=\"font-family: 굴림체;\"><b>입니다</b></span></p>','2022-09-28 20:03:04',0,0,0,'ep'),
(23,'0','제목','<p>내용</p>','2022-10-01 19:54:50',1,0,0,'ep'),
(24,'0','제목1','<p>내용2</p>','2022-10-18 07:18:30',1,0,0,'ep'),
(27,'0','공지글','<p>공지글 테스트</p>','2022-10-31 17:15:35',0,0,0,'gong'),
(28,'0','제목','<p>내용</p><p><img src=\"/noteImg/ab9bcc92-cb9b-4280-b50b-8a0d748898a0.jpg\" style=\"width: 905px;\"><br></p>','2022-10-31 18:20:34',0,0,0,'ep'),
(29,'0','1','<p>1<img src=\"/noteImg/ac4fc73c-061b-4ec9-8f92-de212be3adcb.jpg\" style=\"width: 992px;\"></p>','2022-10-31 18:21:49',0,0,0,'ep'),
(30,'0','1','<p>1</p>','2022-10-31 18:22:40',0,0,0,'ep'),
(31,'2','123','<p>123</p>','2022-10-31 18:41:39',0,0,0,'gong'),
(32,'2','테스트 0번째','<p>테스팅</p> 0번째','2022-10-31 20:47:54',1,0,0,'ep'),
(33,'2','테스트 1번째','<p>테스팅</p> 1번째','2022-10-31 20:47:54',0,1,0,'ep'),
(34,'2','테스트 2번째','<p>테스팅</p> 2번째','2022-10-31 20:47:54',1,1,0,'ep'),
(35,'2','테스트 3번째','<p>테스팅</p> 3번째','2022-10-31 20:47:54',1,1,0,'ep'),
(36,'2','테스트 4번째','<p>테스팅</p> 4번째','2022-10-31 20:47:54',1,0,0,'ep'),
(37,'2','테스트 5번째','<p>테스팅</p> 5번째','2022-10-31 20:47:54',0,1,0,'ep'),
(38,'2','테스트 6번째','<p>테스팅</p> 6번째','2022-10-31 20:47:54',0,0,0,'ep'),
(39,'2','테스트 7번째','<p>테스팅</p> 7번째','2022-10-31 20:47:54',0,1,0,'ep'),
(40,'2','테스트 8번째','<p>테스팅</p> 8번째','2022-10-31 20:47:54',0,0,0,'ep'),
(41,'2','테스트 9번째','<p>테스팅</p> 9번째','2022-10-31 20:47:54',0,1,0,'ep'),
(42,'2','테스트 10번째','<p>테스팅</p> 10번째','2022-10-31 20:47:54',0,0,0,'ep'),
(43,'2','테스트 11번째','<p>테스팅</p> 11번째','2022-10-31 20:47:54',0,10,0,'ep'),
(44,'2','테스트 12번째','<p>테스팅</p> 12번째','2022-10-31 20:47:54',0,0,0,'ep'),
(45,'2','테스트 13번째','<p>테스팅</p> 13번째','2022-10-31 20:47:54',0,0,0,'ep'),
(46,'2','테스트 14번째','<p>테스팅</p> 14번째','2022-10-31 20:47:54',0,0,0,'ep'),
(47,'2','테스트 15번째','<p>테스팅</p> 15번째','2022-10-31 20:47:54',0,0,0,'ep'),
(48,'2','테스트 16번째','<p>테스팅</p> 16번째','2022-10-31 20:47:54',0,0,0,'ep'),
(49,'2','테스트 17번째','<p>테스팅</p> 17번째','2022-10-31 20:47:54',0,0,0,'ep'),
(50,'2','테스트 18번째','<p>테스팅</p> 18번째','2022-10-31 20:47:54',0,0,0,'ep'),
(51,'2','테스트 19번째','<p>테스팅</p> 19번째','2022-10-31 20:47:54',0,0,0,'ep'),
(52,'2','테스트 20번째','<p>테스팅</p> 20번째','2022-10-31 20:47:54',0,0,0,'ep'),
(53,'2','테스트 21번째','<p>테스팅</p> 21번째','2022-10-31 20:47:54',0,0,0,'ep'),
(54,'2','테스트 22번째','<p>테스팅</p> 22번째','2022-10-31 20:47:54',0,0,0,'ep'),
(55,'2','테스트 23번째','<p>테스팅</p> 23번째','2022-10-31 20:47:54',0,0,0,'ep'),
(56,'2','테스트 24번째','<p>테스팅</p> 24번째','2022-10-31 20:47:54',0,0,0,'ep'),
(57,'2','테스트 25번째','<p>테스팅</p> 25번째','2022-10-31 20:47:54',0,0,0,'ep'),
(58,'2','테스트 26번째','<p>테스팅</p> 26번째','2022-10-31 20:47:54',0,0,0,'ep'),
(59,'2','테스트 27번째','<p>테스팅</p> 27번째','2022-10-31 20:47:54',0,0,0,'ep'),
(60,'2','테스트 28번째','<p>테스팅</p> 28번째','2022-10-31 20:47:54',0,0,0,'ep'),
(61,'2','테스트 29번째','<p>테스팅</p> 29번째','2022-10-31 20:47:54',0,0,0,'ep'),
(62,'2','테스트 30번째','<p>테스팅</p> 30번째','2022-10-31 20:47:54',0,0,0,'ep'),
(63,'2','테스트 31번째','<p>테스팅</p> 31번째','2022-10-31 20:47:54',0,0,0,'ep'),
(64,'2','테스트 32번째','<p>테스팅</p> 32번째','2022-10-31 20:47:54',0,0,0,'ep'),
(65,'2','테스트 33번째','<p>테스팅</p> 33번째','2022-10-31 20:47:54',0,0,0,'ep'),
(66,'2','테스트 34번째','<p>테스팅</p> 34번째','2022-10-31 20:47:54',0,0,0,'ep'),
(67,'2','테스트 35번째','<p>테스팅</p> 35번째','2022-10-31 20:47:54',0,0,0,'ep'),
(68,'2','테스트 36번째','<p>테스팅</p> 36번째','2022-10-31 20:47:54',0,0,0,'ep'),
(69,'2','테스트 37번째','<p>테스팅</p> 37번째','2022-10-31 20:47:54',0,0,0,'ep'),
(70,'2','테스트 38번째','<p>테스팅</p> 38번째','2022-10-31 20:47:54',0,0,0,'ep'),
(71,'2','테스트 39번째','<p>테스팅</p> 39번째','2022-10-31 20:47:54',0,0,0,'ep'),
(72,'2','테스트중입니다 0번째','<p>예이</p> 0번째','2022-10-31 20:53:20',0,0,0,'ep'),
(73,'2','테스트중입니다 1번째','<p>예이</p> 1번째','2022-10-31 20:53:20',0,0,0,'ep'),
(74,'2','테스트중입니다 2번째','<p>예이</p> 2번째','2022-10-31 20:53:20',0,0,0,'ep'),
(75,'2','테스트중입니다 3번째','<p>예이</p> 3번째','2022-10-31 20:53:20',0,0,0,'ep'),
(76,'2','테스트중입니다 4번째','<p>예이</p> 4번째','2022-10-31 20:53:20',0,0,0,'ep'),
(77,'2','테스트중입니다 5번째','<p>예이</p> 5번째','2022-10-31 20:53:20',0,0,0,'ep'),
(78,'2','테스트중입니다 6번째','<p>예이</p> 6번째','2022-10-31 20:53:20',0,0,0,'ep'),
(79,'2','테스트중입니다 7번째','<p>예이</p> 7번째','2022-10-31 20:53:20',0,0,0,'ep'),
(80,'2','테스트중입니다 8번째','<p>예이</p> 8번째','2022-10-31 20:53:20',0,0,0,'ep'),
(81,'2','테스트중입니다 9번째','<p>예이</p> 9번째','2022-10-31 20:53:20',0,0,0,'ep'),
(82,'2','테스트중입니다 10번째','<p>예이</p> 10번째','2022-10-31 20:53:20',0,0,0,'ep'),
(83,'2','테스트중입니다 11번째','<p>예이</p> 11번째','2022-10-31 20:53:20',0,0,0,'ep'),
(84,'2','테스트중입니다 12번째','<p>예이</p> 12번째','2022-10-31 20:53:20',0,0,0,'ep'),
(85,'2','테스트중입니다 13번째','<p>예이</p> 13번째','2022-10-31 20:53:20',0,0,0,'ep'),
(86,'2','테스트중입니다 14번째','<p>예이</p> 14번째','2022-10-31 20:53:20',0,0,0,'ep'),
(87,'2','테스트중입니다 15번째','<p>예이</p> 15번째','2022-10-31 20:53:20',0,0,0,'ep'),
(88,'2','테스트중입니다 16번째','<p>예이</p> 16번째','2022-10-31 20:53:20',0,0,0,'ep'),
(89,'2','테스트중입니다 17번째','<p>예이</p> 17번째','2022-10-31 20:53:20',0,0,0,'ep'),
(90,'2','테스트중입니다 18번째','<p>예이</p> 18번째','2022-10-31 20:53:20',0,0,0,'ep'),
(91,'2','테스트중입니다 19번째','<p>예이</p> 19번째','2022-10-31 20:53:20',0,0,0,'ep'),
(92,'2','테스트중입니다 20번째','<p>예이</p> 20번째','2022-10-31 20:53:20',0,0,0,'ep'),
(93,'2','테스트중입니다 21번째','<p>예이</p> 21번째','2022-10-31 20:53:20',0,0,0,'ep'),
(94,'2','테스트중입니다 22번째','<p>예이</p> 22번째','2022-10-31 20:53:20',0,0,0,'ep'),
(95,'2','테스트중입니다 23번째','<p>예이</p> 23번째','2022-10-31 20:53:20',0,0,0,'ep'),
(96,'2','테스트중입니다 24번째','<p>예이</p> 24번째','2022-10-31 20:53:20',0,0,0,'ep'),
(97,'2','테스트중입니다 25번째','<p>예이</p> 25번째','2022-10-31 20:53:20',0,0,0,'ep'),
(98,'2','테스트중입니다 26번째','<p>예이</p> 26번째','2022-10-31 20:53:20',0,0,0,'ep'),
(99,'2','테스트중입니다 27번째','<p>예이</p> 27번째','2022-10-31 20:53:20',0,0,0,'ep'),
(100,'2','테스트중입니다 28번째','<p>예이</p> 28번째','2022-10-31 20:53:20',0,0,0,'ep'),
(101,'2','테스트중입니다 29번째','<p>예이</p> 29번째','2022-10-31 20:53:20',0,0,0,'ep'),
(102,'2','테스트중입니다 30번째','<p>예이</p> 30번째','2022-10-31 20:53:20',0,0,0,'ep'),
(103,'2','테스트중입니다 31번째','<p>예이</p> 31번째','2022-10-31 20:53:20',0,0,0,'ep'),
(104,'2','테스트중입니다 32번째','<p>예이</p> 32번째','2022-10-31 20:53:20',0,0,0,'ep'),
(105,'2','테스트중입니다 33번째','<p>예이</p> 33번째','2022-10-31 20:53:20',0,0,0,'ep'),
(106,'2','테스트중입니다 34번째','<p>예이</p> 34번째','2022-10-31 20:53:20',0,0,0,'ep'),
(107,'2','테스트중입니다 35번째','<p>예이</p> 35번째','2022-10-31 20:53:20',0,0,0,'ep'),
(108,'2','테스트중입니다 36번째','<p>예이</p> 36번째','2022-10-31 20:53:20',0,0,0,'ep'),
(109,'2','테스트중입니다 37번째','<p>예이</p> 37번째','2022-10-31 20:53:20',0,0,0,'ep'),
(110,'2','테스트중입니다 38번째','<p>예이</p> 38번째','2022-10-31 20:53:20',0,0,0,'ep'),
(111,'2','테스트중입니다 39번째','<p>예이</p> 39번째','2022-10-31 20:53:20',0,0,0,'ep'),
(112,'2','테스트중입니다 0번째','<p>ㅇㅂㅇ</p> 0번째','2022-10-31 20:53:23',0,0,0,'ep'),
(113,'2','테스트중입니다 1번째','<p>ㅇㅂㅇ</p> 1번째','2022-10-31 20:53:23',0,0,0,'ep'),
(114,'2','테스트중입니다 2번째','<p>ㅇㅂㅇ</p> 2번째','2022-10-31 20:53:23',0,0,0,'ep'),
(115,'2','테스트중입니다 3번째','<p>ㅇㅂㅇ</p> 3번째','2022-10-31 20:53:23',0,0,0,'ep'),
(116,'2','테스트중입니다 4번째','<p>ㅇㅂㅇ</p> 4번째','2022-10-31 20:53:23',0,0,0,'ep'),
(117,'2','테스트중입니다 5번째','<p>ㅇㅂㅇ</p> 5번째','2022-10-31 20:53:23',0,0,0,'ep'),
(118,'2','테스트중입니다 6번째','<p>ㅇㅂㅇ</p> 6번째','2022-10-31 20:53:23',0,0,0,'ep'),
(119,'2','테스트중입니다 7번째','<p>ㅇㅂㅇ</p> 7번째','2022-10-31 20:53:23',0,0,0,'ep'),
(120,'2','테스트중입니다 8번째','<p>ㅇㅂㅇ</p> 8번째','2022-10-31 20:53:23',0,0,0,'ep'),
(121,'2','테스트중입니다 9번째','<p>ㅇㅂㅇ</p> 9번째','2022-10-31 20:53:23',0,0,0,'ep'),
(122,'2','테스트중입니다 10번째','<p>ㅇㅂㅇ</p> 10번째','2022-10-31 20:53:23',0,0,0,'ep'),
(123,'2','테스트중입니다 11번째','<p>ㅇㅂㅇ</p> 11번째','2022-10-31 20:53:23',0,0,0,'ep'),
(124,'2','테스트중입니다 12번째','<p>ㅇㅂㅇ</p> 12번째','2022-10-31 20:53:23',0,0,0,'ep'),
(125,'2','테스트중입니다 13번째','<p>ㅇㅂㅇ</p> 13번째','2022-10-31 20:53:23',0,0,0,'ep'),
(126,'2','테스트중입니다 14번째','<p>ㅇㅂㅇ</p> 14번째','2022-10-31 20:53:23',0,0,0,'ep'),
(127,'2','테스트중입니다 15번째','<p>ㅇㅂㅇ</p> 15번째','2022-10-31 20:53:23',0,0,0,'ep'),
(128,'2','테스트중입니다 16번째','<p>ㅇㅂㅇ</p> 16번째','2022-10-31 20:53:23',0,0,0,'ep'),
(129,'2','테스트중입니다 17번째','<p>ㅇㅂㅇ</p> 17번째','2022-10-31 20:53:23',0,0,0,'ep'),
(130,'2','테스트중입니다 18번째','<p>ㅇㅂㅇ</p> 18번째','2022-10-31 20:53:23',0,0,0,'ep'),
(131,'2','테스트중입니다 19번째','<p>ㅇㅂㅇ</p> 19번째','2022-10-31 20:53:23',0,0,0,'ep'),
(132,'2','테스트중입니다 20번째','<p>ㅇㅂㅇ</p> 20번째','2022-10-31 20:53:23',0,0,0,'ep'),
(133,'2','테스트중입니다 21번째','<p>ㅇㅂㅇ</p> 21번째','2022-10-31 20:53:23',0,0,0,'ep'),
(134,'2','테스트중입니다 22번째','<p>ㅇㅂㅇ</p> 22번째','2022-10-31 20:53:23',0,0,0,'ep'),
(135,'2','테스트중입니다 23번째','<p>ㅇㅂㅇ</p> 23번째','2022-10-31 20:53:23',0,0,0,'ep'),
(136,'2','테스트중입니다 24번째','<p>ㅇㅂㅇ</p> 24번째','2022-10-31 20:53:23',0,0,0,'ep'),
(137,'2','테스트중입니다 25번째','<p>ㅇㅂㅇ</p> 25번째','2022-10-31 20:53:23',0,0,0,'ep'),
(138,'2','테스트중입니다 26번째','<p>ㅇㅂㅇ</p> 26번째','2022-10-31 20:53:23',0,0,0,'ep'),
(139,'2','테스트중입니다 27번째','<p>ㅇㅂㅇ</p> 27번째','2022-10-31 20:53:23',0,0,0,'ep'),
(140,'2','테스트중입니다 28번째','<p>ㅇㅂㅇ</p> 28번째','2022-10-31 20:53:23',0,0,0,'ep'),
(141,'2','테스트중입니다 29번째','<p>ㅇㅂㅇ</p> 29번째','2022-10-31 20:53:23',0,0,0,'ep'),
(142,'2','테스트중입니다 30번째','<p>ㅇㅂㅇ</p> 30번째','2022-10-31 20:53:23',0,0,0,'ep'),
(143,'2','테스트중입니다 31번째','<p>ㅇㅂㅇ</p> 31번째','2022-10-31 20:53:23',0,0,0,'ep'),
(144,'2','테스트중입니다 32번째','<p>ㅇㅂㅇ</p> 32번째','2022-10-31 20:53:23',0,0,0,'ep'),
(145,'2','테스트중입니다 33번째','<p>ㅇㅂㅇ</p> 33번째','2022-10-31 20:53:23',0,0,0,'ep'),
(146,'2','테스트중입니다 34번째','<p>ㅇㅂㅇ</p> 34번째','2022-10-31 20:53:23',0,0,0,'ep'),
(147,'2','테스트중입니다 35번째','<p>ㅇㅂㅇ</p> 35번째','2022-10-31 20:53:23',0,0,0,'ep'),
(148,'2','테스트중입니다 36번째','<p>ㅇㅂㅇ</p> 36번째','2022-10-31 20:53:23',0,0,0,'ep'),
(149,'2','테스트중입니다 37번째','<p>ㅇㅂㅇ</p> 37번째','2022-10-31 20:53:23',0,0,0,'ep'),
(150,'2','테스트중입니다 38번째','<p>ㅇㅂㅇ</p> 38번째','2022-10-31 20:53:23',0,0,0,'ep'),
(151,'2','테스트중입니다 39번째','<p>ㅇㅂㅇ</p> 39번째','2022-10-31 20:53:23',0,1,0,'ep');
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `novel`
--

DROP TABLE IF EXISTS `novel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `novel` (
  `n_num` int(11) NOT NULL AUTO_INCREMENT,
  `u_num` varchar(30) CHARACTER SET latin1 NOT NULL,
  `n_title` varchar(300) NOT NULL,
  `n_introduction` varchar(1500) DEFAULT NULL,
  `n_good` int(11) DEFAULT 0,
  `n_date` varchar(30) CHARACTER SET latin1 DEFAULT NULL,
  `n_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'free',
  `n_monopoly` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'free',
  `n_fin` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'doing',
  `n_stop` int(11) DEFAULT 0,
  `n_cover` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`n_num`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `novel`
--

LOCK TABLES `novel` WRITE;
/*!40000 ALTER TABLE `novel` DISABLE KEYS */;
INSERT INTO `novel` VALUES
(1,'1','테스트','작품소개',0,NULL,'prime','only','done',0,'8e33f41b-0084-41cf-9451-b221231db85d.jpg'),
(2,'19','1','1',0,NULL,'free','free','doing',0,'8e33f41b-0084-41cf-9451-b221231db85d.jpg'),
(3,'19','1','1',0,NULL,'free','free','doing',0,'8e33f41b-0084-41cf-9451-b221231db85d.jpg'),
(4,'1','테스트2','작품소개',0,NULL,'prime','only','done',0,'8e33f41b-0084-41cf-9451-b221231db85d.jpg'),
(5,'1','테스트','작품소개',0,NULL,'prime','only','done',0,'8e33f41b-0084-41cf-9451-b221231db85d.jpg'),
(6,'19','테스트','작품소개',0,NULL,'prime','only','doing',0,'c2f62b38-7020-4308-b842-fbc37a920836.png'),
(7,'19','테스트','하나',0,NULL,'free','free','doing',0,'503c7c11-58d8-4eb3-8710-be1c4c146bff.png'),
(8,'19','테스트','테스트',0,NULL,'prime','only','doing',0,NULL),
(9,'19','테스트','테스트',0,NULL,'prime','only','doing',0,'1263219e-f141-4088-8d0c-3e00c12c4b99.png'),
(10,'19','테스트2','테스팅 중입니다.',0,NULL,'free','only','done',0,'c4d1b8d3-ef81-407b-9d15-c6fecfb01e0a.png');
/*!40000 ALTER TABLE `novel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_warning`
--

DROP TABLE IF EXISTS `r_warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_warning` (
  `rw_num` int(10) NOT NULL AUTO_INCREMENT,
  `r_num` int(11) NOT NULL,
  `u_num` int(11) NOT NULL,
  PRIMARY KEY (`rw_num`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_warning`
--

LOCK TABLES `r_warning` WRITE;
/*!40000 ALTER TABLE `r_warning` DISABLE KEYS */;
INSERT INTO `r_warning` VALUES
(1,151,1),
(2,35,19);
/*!40000 ALTER TABLE `r_warning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply` (
  `r_num` int(11) NOT NULL AUTO_INCREMENT,
  `u_num` varchar(10) NOT NULL,
  `r_rnum` int(10) NOT NULL DEFAULT 0,
  `r_memo` varchar(570) NOT NULL,
  `m_num` varchar(10) NOT NULL,
  `n_num` varchar(10) NOT NULL,
  `r_date` datetime NOT NULL DEFAULT current_timestamp(),
  `r_good` int(10) NOT NULL DEFAULT 0,
  `r_bad` int(10) NOT NULL DEFAULT 0,
  `r_baby` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`r_num`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES
(1,'19',0,'1','4','1','2022-11-04 23:51:02',3,0,1),
(2,'19',0,'테스트메모','4','1','2022-11-04 23:51:02',0,2,3),
(3,'19',0,'테스트','4','1','2022-11-04 23:51:02',1,0,0),
(4,'18',2,'ㄹ','4','1','2022-11-04 23:51:02',2,0,0),
(5,'18',2,'ㄹ','4','1','2022-11-04 23:51:02',1,3,0),
(6,'19',0,'1','4','1','2022-11-04 23:51:02',5,0,0),
(7,'19',0,'테스트메모','4','1','2022-11-04 23:51:02',0,0,1),
(8,'19',0,'테스트','4','1','2022-11-04 23:51:02',4,0,0),
(9,'18',2,'ㄹ','4','1','2022-11-04 23:51:02',0,0,0),
(10,'18',7,'ㄹ','4','1','2022-11-04 23:51:02',0,0,0),
(11,'0',1,'츄라이','4','1','2022-11-05 02:20:55',0,0,0),
(12,'19',1,'츄라이','4','1','2022-11-05 02:21:20',0,0,0),
(13,'0',0,'ㅇㅂㅇ','4','1','2022-11-05 02:25:24',0,0,0),
(14,'1',0,'댓글테스트','4','1','2022-11-05 02:25:43',0,0,0),
(15,'1',0,'한번더','4','1','2022-11-05 02:26:51',0,0,0),
(16,'0',0,'d','4','1','2022-11-07 00:01:41',0,0,0),
(17,'0',0,'d','4','1','2022-11-07 00:06:02',0,0,0),
(18,'0',0,'댓글등록','4','1','2022-11-07 00:09:38',0,0,0),
(19,'0',0,'댓글','4','1','2022-11-07 00:11:32',0,0,0),
(20,'0',0,'ㅇㅂㅇ','4','1','2022-11-07 00:11:59',0,0,0),
(21,'0',0,'dqd','4','1','2022-11-07 00:13:30',0,0,0),
(22,'0',0,'테스트','4','1','2022-11-07 01:25:27',0,0,0),
(23,'0',1,'ㅇㅂㅇ','4','1','2022-11-07 01:29:47',0,0,0),
(24,'0',2,'ㅇㅂㅇ','4','1','2022-11-07 01:29:59',0,0,0),
(25,'0',0,'ㅇㄴ','4','1','2022-11-07 01:46:31',0,0,0),
(26,'0',3,'몰?루','4','1','2022-11-07 01:54:14',0,0,0),
(27,'19',0,'<img src=\"../imoticon/imo007.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px; margin-bottom:8px;\"><br>아루','4','1','2022-11-07 03:20:09',0,0,0),
(28,'0',0,'<img src=\"../imoticon/imo009.jpg\" className=\"imo01\" alt=\"뿅\"><br>개추','4','1','2022-11-07 03:24:46',0,0,0),
(29,'19',2,'<img src=\"../imoticon/imo006.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px; margin-bottom:8px;><br>해줘!','4','1','2022-11-07 03:26:38',0,0,0),
(30,'19',0,'<img src=\"../imoticon/imo009.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>개추','4','1','2022-11-07 03:30:18',0,0,0),
(31,'19',0,'<img src=\"../imoticon/imo004.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>d','4','1','2022-11-07 03:37:11',0,0,0),
(32,'0',0,'댓글','4','1','2022-11-07 03:55:30',0,0,0),
(33,'19',14,'<img src=\"../imoticon/imo003.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>아!루!','4','1','2022-11-07 03:56:03',0,0,0),
(34,'0',0,'<img src=\"../imoticon/imo004.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>더줘','151','2','2022-11-14 05:41:22',0,0,0),
(35,'19',0,'<img src=\"../imoticon/imo003.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>','151','2','2022-11-14 05:41:46',6,6,0),
(36,'0',35,'<img src=\"../imoticon/imo004.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>','151','2','2022-11-14 05:46:52',0,0,0),
(37,'19',35,'<img src=\"../imoticon/imo001.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>','151','2','2022-11-14 05:47:09',1,0,0),
(38,'19',0,'<img src=\"../imoticon/imo003.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>','37','2','2022-11-14 08:27:21',1,1,0),
(39,'19',0,'<img src=\"../imoticon/imo008.jpg\" className=\"imo01\" alt=\"뿅\" style=\"width: 120px; height: 120px;\"><br>','37','2','2022-11-14 08:27:29',1,1,0);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `u_num` int(11) NOT NULL AUTO_INCREMENT,
  `u_mail` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  `u_pass` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `u_nick` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '닉네임 없음',
  `u_ok` int(11) NOT NULL DEFAULT 0,
  `u_level` int(11) NOT NULL DEFAULT 0,
  `u_like` varchar(50) CHARACTER SET utf8mb3 DEFAULT '없음',
  `u_myself` text CHARACTER SET latin1 DEFAULT NULL,
  `u_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `u_pic` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`u_num`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,'kalsollrem@naver.com','1234','닉네임',1,0,'없음',NULL,'done',NULL),
(18,'kalsollrem@gmail.com','kalsollrem@gmail.com','kalsollrem@g',0,0,'없음',NULL,'lcgdgylfoieuqcf',NULL),
(19,'1','1','1',1,0,'없음',NULL,'ledewbhciqibttl',NULL),
(20,'test@123456789','test@123456789','test@123',1,0,'없음',NULL,'mqhrslljstqffha',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'novel'
--

--
-- Dumping routines for database 'novel'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-18 22:02:28
