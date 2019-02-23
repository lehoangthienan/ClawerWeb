/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : webcrawler

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-06-16 22:03:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(355) COLLATE utf8mb4_bin DEFAULT NULL,
  `date_add` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=978 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(355) COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(355) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `originalPrice` decimal(10,0) DEFAULT NULL,
  `ratingScore` decimal(4,1) DEFAULT '0.0',
  `date_add` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
