USE taphoait;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: taphoait
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Dumping data for table `brand_tb`
--

LOCK TABLES `brand_tb` WRITE;
/*!40000 ALTER TABLE `brand_tb` DISABLE KEYS */;
INSERT INTO `brand_tb` VALUES ('B001','Brand A'),('B002','Brand B'),('B003','Brand C');
/*!40000 ALTER TABLE `brand_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category_tb`
--

LOCK TABLES `category_tb` WRITE;
/*!40000 ALTER TABLE `category_tb` DISABLE KEYS */;
INSERT INTO `category_tb` VALUES ('C001','Category k'),('C002','Category Y'),('C003','Category Z'),('C4','ttt'),('C5','asdasd');
/*!40000 ALTER TABLE `category_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `discount_tb`
--

LOCK TABLES `discount_tb` WRITE;
/*!40000 ALTER TABLE `discount_tb` DISABLE KEYS */;
INSERT INTO `discount_tb` VALUES ('D001',10,'2024-01-01','2024-01-31',0),('D002',20,'2024-02-01','2024-02-28',0),('D3',33,'2024-10-10','2024-10-31',0),('D4',33,'2024-10-02','2024-10-24',0),('D5',14,'2024-10-23','2024-10-31',0);
/*!40000 ALTER TABLE `discount_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image_tb`
--

LOCK TABLES `image_tb` WRITE;
/*!40000 ALTER TABLE `image_tb` DISABLE KEYS */;
INSERT INTO `image_tb` VALUES (1,'pro1.jpg'),(2,'pro1.jpg'),(3,'pro1.jpg'),(4,'Screenshot 2024-07-12 220328.png'),(5,'1_cb96f0c5cbf541f9b8e65202cc0368ab_master.jpg'),(6,'1_d5da4d418ece4af8aac3dfffd08f7688_master.jpg'),(7,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/2d2ed83789dee4fb08a385961bc0bd86.jpg?alt=media'),(8,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/34ee9826029cd66499c3e5993d7c9bed.jpg?alt=media'),(9,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/1828d026a93f77a1f9554789155c0aee.jpg?alt=media'),(10,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/best-product-1.jpg?alt=media'),(11,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/best-product-2.jpg?alt=media'),(12,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/44d940ab1c14f4e68d885e9bad82abf6.jpg?alt=media'),(13,'https://firebasestorage.googleapis.com/v0/b/savefileimage.appspot.com/o/4d4ff0ebb595d5b3247e21d3f5955d08.jpg?alt=media');
/*!40000 ALTER TABLE `image_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_detail_tb`
--

LOCK TABLES `order_detail_tb` WRITE;
/*!40000 ALTER TABLE `order_detail_tb` DISABLE KEYS */;
INSERT INTO `order_detail_tb` VALUES ('O001','P001',2),('O002','P003',1),('O003','P001',3),('O004','P002',2),('O005','P003',4),('O006','P001',1),('O006','P002',1),('O007','P001',2),('O008','P002',3),('O009','P003',4),('O010','P001',1),('O010','P002',2);
/*!40000 ALTER TABLE `order_detail_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment_tb`
--

LOCK TABLES `payment_tb` WRITE;
/*!40000 ALTER TABLE `payment_tb` DISABLE KEYS */;
INSERT INTO `payment_tb` VALUES ('O001','Online Banking','Chưa thanh toán'),('O002','Thanh toán khi nhận hàng','Chưa thanh toán'),('O003','Thanh toán khi nhận hàng','Chưa thanh toán'),('O004','Online Banking','Đã thanh toán'),('O005','Thanh toán khi nhận hàng','Chưa thanh toán'),('O006','Online Banking','Đã thanh toán'),('O007','Thanh toán khi nhận hàng','Chưa thanh toán'),('O008','Online Banking','Chưa thanh toán'),('O009','Thanh toán khi nhận hàng','Chưa thanh toán'),('O010','Online Banking','Đã thanh toán');
/*!40000 ALTER TABLE `payment_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_tb`
--

LOCK TABLES `product_tb` WRITE;
/*!40000 ALTER TABLE `product_tb` DISABLE KEYS */;
INSERT INTO `product_tb` VALUES ('P001','Product 1','B001','C001','D001',10,'<p><strong>Dao sản hảo tung của bing chi ling</strong></p>\r\n<p><strong>h&aacute;hdahdhd</strong></p>\r\n<p><strong>&aacute;hdahhads</strong></p>\r\n<p><strong>ahsdadhahdahsdahsd</strong></p>\r\n<p><strong>hadahsdhadhahd</strong></p>\r\n<p>&nbsp;</p>',15000,50,34,1,'S001'),('P002','Product 2','B002','C002',NULL,11,'Description of Product 2',5000000,60,30,1,'S002'),('P003','Product 3','B003','C003','D002',9,'Description of Product 3',20000000,70,20,1,'S002'),('P10','phong','B002','C002','D001',12,'a',13333,1,12,1,'S001'),('P11','quang','B002','C002','D001',12,'a',123444,3123,12,1,'S002'),('P4','PCBH GAMING FORGE CORE I7 14700K / Z790 WIFI / RTX3060 12GB / 32GB DDR5 / 500GB','B001','C002',NULL,4,'PCBH GAMING FORGE CORE I7 14700K / Z790 WIFI / RTX3060 12GB / 32GB DDR5 / 500GB',5000000,254,1,1,'S001'),('P5','PCBH AMD LUX RYZEN 5 5600X / B550M / RX6600 8GB / 16GB / 256GB','B001','C001',NULL,5,'PCBH AMD LUX RYZEN 5 5600X / B550M / RX6600 8GB / 16GB / 256GB\r\n',3000000,123,12,1,'S001'),('P6','PCBH AMD ALPHARD ARC RYZEN 5 5500 / B450M / RX6600 8GB / 16GB / 256GB','B001','C001',NULL,6,'PCBH AMD ALPHARD ARC RYZEN 5 5500 / B450M / RX6600 8GB / 16GB / 256GB\r\n',16000000,123,167,1,'S001'),('P7','phong','B002','C001','D002',6,'khongggg',20000000,1,23,1,'S001'),('P8','asddasd','B001','C001','D001',12,'hmm',15455,123,1,1,'S001'),('P9','asd','B003','C003',NULL,13,'a',10000000,123,6,1,'S002');
/*!40000 ALTER TABLE `product_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role_user_tb`
--

LOCK TABLES `role_user_tb` WRITE;
/*!40000 ALTER TABLE `role_user_tb` DISABLE KEYS */;
INSERT INTO `role_user_tb` VALUES (1,'ADMIN'),(2,'CUSTOMER'),(3,'SELLER'),(4,'SHIPPER');
/*!40000 ALTER TABLE `role_user_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shop_order_tb`
--

LOCK TABLES `shop_order_tb` WRITE;
/*!40000 ALTER TABLE `shop_order_tb` DISABLE KEYS */;
INSERT INTO `shop_order_tb`
VALUES
    ('O001', 'U002', 'U002', '2024-06-05', 200, 20, '2024-06-10', 'Nguyen Van A', '123321231213', 'đường 23, phường 3, thành phố Biên Hòa, tỉnh Đồng Nai', 'Đã xác nhận', '2024-06-05 10:30:00'),
    ('O002', 'U004', 'U002', '2024-06-06', 300, 30, '2024-06-12', 'John Doe', '0987654321', '456 Oak St, Townsville', 'Đã xác nhận', '2024-06-06 11:00:00'),
    ('O003', 'U002', 'U002', '2024-06-07', 250, 25, '2024-06-13', 'Nguyen Thi B', '1112223333', '123 Elm St, Cityville', 'Chờ xác nhận', '2024-06-07 12:15:00'),
    ('O004', 'U003', 'U002', '2024-06-08', 350, 35, '2024-06-14', 'Tran Van C', '2223334444', '789 Pine St, Villagetown', 'Đã xác nhận', '2024-06-08 13:45:00'),
    ('O005', 'U004', 'U002', '2024-06-09', 400, 40, '2024-06-15', 'Le Thi D', '3334445555', '101 Maple St, Hamlet', 'Hoàn thành', '2024-06-09 14:30:00'),
    ('O006', 'U002', 'U002', '2024-06-10', 450, 45, '2024-06-16', 'Pham Van E', '4445556666', '567 Cedar St, Metropolis', 'Hoàn thành', '2024-06-10 15:20:00'),
    ('O007', 'U005', 'U002', '2024-07-01', 500, 50, '2024-07-07', 'Nguyen Van F', '5556667777', '234 Cedar St, Smalltown', 'Chờ xác nhận', '2024-07-01 16:10:00'),
    ('O008', 'U006', 'U002', '2024-07-02', 600, 60, '2024-07-08', 'Hoang Thi G', '6667778888', '345 Spruce St, Bigcity', 'Hoàn thành', '2024-07-02 17:00:00'),
    ('O009', 'U007', 'U002', '2024-07-03', 700, 70, '2024-07-09', 'Nguyen Van H', '7778889999', '456 Willow St, Largetown', 'Đang giao', '2024-07-03 18:45:00'),
    ('O010', 'U008', 'U002', '2024-07-04', 800, 80, '2024-07-10', 'Tran Thi I', '8889990000', '567 Maple St, Uptown', 'Hoàn thành', '2024-07-04 19:30:00');
/*!40000 ALTER TABLE `shop_order_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shopping_cart_tb`
--

LOCK TABLES `shopping_cart_tb` WRITE;
/*!40000 ALTER TABLE `shopping_cart_tb` DISABLE KEYS */;
INSERT INTO `shopping_cart_tb` VALUES ('U002','P001',2),('U003','P002',1),('U004','P4',1),('U005','P001',1),('U005','P002',2),('U005','P003',1),('U006','P001',2),('U006','P4',1),('U006','P5',3),('U007','P002',1),('U007','P5',2),('U007','P6',1),('U008','P003',1),('U008','P4',2),('U008','P6',1),('U11','P001',1),('U12','P001',1),('U9','P001',1);
/*!40000 ALTER TABLE `shopping_cart_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supplier_tb`
--

LOCK TABLES `supplier_tb` WRITE;
/*!40000 ALTER TABLE `supplier_tb` DISABLE KEYS */;
INSERT INTO `supplier_tb` VALUES ('S001','Supplier 1','234 Elm St','3334445555','supplier1@example.com'),('S002','Supplier 2','567 Birch St','2223334444','supplier2@example.com');
/*!40000 ALTER TABLE `supplier_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tokens_tb`
--

LOCK TABLES `tokens_tb` WRITE;
/*!40000 ALTER TABLE `tokens_tb` DISABLE KEYS */;
INSERT INTO `tokens_tb` VALUES (242,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFuYUBleGFtcGxlLmNvbSIsImlhdCI6MTczMDA4MTE1MiwiZXhwIjoxNzMwMDgyNTkyfQ.x2_eQHHfdydttl_uA0-sQQp5wrR5pvvwQ8S7k1hFVE8',NULL,0,0,'diana@example.com');
/*!40000 ALTER TABLE `tokens_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_tb`
--

LOCK TABLES `user_tb` WRITE;
/*!40000 ALTER TABLE `user_tb` DISABLE KEYS */;
INSERT INTO `user_tb` VALUES ('U001','Alice Smith','123 Main St','1234567890','Nữ','alice@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U002','Bob Johnson','456 Oak St','0987654321','Nam','bob@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',3,1),
                             ('U003','Charlie Brown','789 Pine St','5556667777','Nam','charlie@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',4,1),
                             ('U004','Diana Prince','101 Maple St','4445556666','Nữ','diana@example.com','$2a$10$trT7OMIGOaJcIhcmjL9rR.NyO0ROnWHdoj5.osLIFhO3si1SzEj/.',1,1),
                             ('U005','Eve Adams','234 Cedar St','1112223333','Nam','eve@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U006','Frank Black','345 Spruce St','2223334444','Nữ','frank@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',3,1),
                             ('U007','Grace Clark','456 Willow St','3334445555','Nam','grace@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',4,1),
                             ('U008','Hank Davis','567 Maple St','4445456666','Nữ','hank@example.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',1,1),
                             ('U10','Nguyễn Quang',NULL,NULL,NULL,'ndquangtestsend@gmail.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U11','Thanh Phong Lê',NULL,NULL,NULL,'phongga0903@gmail.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U12','Le Thanh Phong',NULL,NULL,NULL,'22110198@student.hcmute.edu.vn','$2a$10$336lllYRiAh7xwxUqiVvFu1ydlYk6aKuVcIskc9cTIo8sLlLc0n0C',2,1),
                             ('U13','Nguyễn Hoài Bảo',NULL,'231412412',NULL,'22110108@student.hcmute.edu.vn','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U14','31.Lê Thanh Phong - 12C1',NULL,NULL,NULL,'occholacothatkk@gmail.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1),
                             ('U15','haro',NULL,'1233457567',NULL,'22110134@student.hcmute.edu.vn','$2a$10$zXevvaa.ySduRQIy4NgiZuIhguhLBX32OTn.GDUERCEAJZX3Srbxe',2,1),
                             ('U9','phongga088',NULL,'0815474739',NULL,'phongga088@gmail.com','$2a$10$w1RrbKx6jJ1VklqQt516YunGacRnZ4/7B3hlDbrD4OLm5Tc0RfVUe',2,1);
/*!40000 ALTER TABLE `user_tb` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-28  9:40:39

