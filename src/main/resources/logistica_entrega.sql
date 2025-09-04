CREATE DATABASE  IF NOT EXISTS `logistica_entrega` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `logistica_entrega`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: logistica_entrega
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `cpf_cnpj` varchar(20) NOT NULL,
  `endereco` varchar(60) NOT NULL,
  `cidade` varchar(60) NOT NULL,
  `estado` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pedido_id` int NOT NULL,
  `motorista_id` int NOT NULL,
  `data_saida` datetime NOT NULL,
  `data_entrega` datetime NOT NULL,
  `status_entrega` enum('EM_ROTA','ENTREGUE','ATRASADA') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pedido_id` (`pedido_id`),
  KEY `motorista_id` (`motorista_id`),
  CONSTRAINT `entrega_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `entrega_ibfk_2` FOREIGN KEY (`motorista_id`) REFERENCES `motorista` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicoEntrega`
--

DROP TABLE IF EXISTS `historicoEntrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historicoEntrega` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entrega_id` int NOT NULL,
  `data_evento` date NOT NULL,
  `descricao` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `entrega_id` (`entrega_id`),
  CONSTRAINT `historicoEntrega_ibfk_1` FOREIGN KEY (`entrega_id`) REFERENCES `entrega` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicoEntrega`
--

LOCK TABLES `historicoEntrega` WRITE;
/*!40000 ALTER TABLE `historicoEntrega` DISABLE KEYS */;
/*!40000 ALTER TABLE `historicoEntrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motorista`
--

DROP TABLE IF EXISTS `motorista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motorista` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `cnh` varchar(20) NOT NULL,
  `veiculo` varchar(60) NOT NULL,
  `cidade_base` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motorista`
--

LOCK TABLES `motorista` WRITE;
/*!40000 ALTER TABLE `motorista` DISABLE KEYS */;
/*!40000 ALTER TABLE `motorista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `data_pedido` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `volume_m3` double(8,2) NOT NULL,
  `peso_kg` double(8,2) NOT NULL,
  `status_pedido` enum('PENDENTE','ENTREGUE','CANCELADO') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_id` (`cliente_id`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-04 16:36:40
