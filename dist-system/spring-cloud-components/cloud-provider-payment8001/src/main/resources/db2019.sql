SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

# Table structure for payment
CREATE DATABASE `db2019`;
USE `db2019`;

DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` BIGINT(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `serial` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

# Records of payment
INSERT INTO `payment` VALUES (20, '胡文飞');
INSERT INTO `payment` VALUES (21, 'huwenfei');
INSERT INTO `payment` VALUES (22, 'huwenfei');
INSERT INTO `payment` VALUES (23, 'huwenfei');
INSERT INTO `payment` VALUES (24, 'huwenfei');
INSERT INTO `payment` VALUES (25, 'huwenfei');
INSERT INTO `payment` VALUES (26, 'huwenfei');
INSERT INTO `payment` VALUES (27, 'huwenfei');
INSERT INTO `payment` VALUES (28, 'huwenfei');
INSERT INTO `payment` VALUES (29, 'huwenfei');
INSERT INTO `payment` VALUES (30, 'huwenfei');
INSERT INTO `payment` VALUES (31, 'huwenfei');
INSERT INTO `payment` VALUES (32, 'huwenfei');

SET FOREIGN_KEY_CHECKS = 1;
