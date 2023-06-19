SELECT * FROM practise.product;CREATE TABLE `product` (
  `idproduct` int NOT NULL AUTO_INCREMENT,
  `productprice` int DEFAULT NULL,
  `productmass` int DEFAULT NULL,
  `tarrif` int DEFAULT '0',
  `productname` varchar(45) DEFAULT NULL,
  `producttype` varchar(45) DEFAULT NULL,
  `productresprice` int DEFAULT NULL,
  PRIMARY KEY (`idproduct`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
