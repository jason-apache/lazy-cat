/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : jpa

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 16/04/2021 14:52:32
*/

SET NAMES utf8mb4;$
SET FOREIGN_KEY_CHECKS = 0;$

-- ----------------------------
-- Table structure for file_content
-- ----------------------------
DROP TABLE IF EXISTS `file_content`;$
CREATE TABLE `file_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_id` int(0) NOT NULL,
  `dir_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for ftp_dir
-- ----------------------------
DROP TABLE IF EXISTS `ftp_dir`;$
CREATE TABLE `ftp_dir`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for ftp_file
-- ----------------------------
DROP TABLE IF EXISTS `ftp_file`;$
CREATE TABLE `ftp_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dir_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for office
-- ----------------------------
DROP TABLE IF EXISTS `office`;$
CREATE TABLE `office`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `level` int(0) NOT NULL,
  `modify_counter` int(0) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;$
CREATE TABLE `user`  (
  `id` int(0) NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `education` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modify_counter` int(0) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23152 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for user_dir
-- ----------------------------
DROP TABLE IF EXISTS `user_dir`;$
CREATE TABLE `user_dir`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;$
CREATE TABLE `user_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `dir_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

DROP TABLE IF EXISTS `sequence`;$
CREATE TABLE `sequence`  (
  `seq_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `current_val` int(0) NOT NULL DEFAULT 0 COMMENT '?????????',
  `increment_val` int(0) NOT NULL DEFAULT 1 COMMENT '?????????????????????',
  PRIMARY KEY (`seq_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;$

-- jpa.resource definition
DROP TABLE IF EXISTS `resource`;$
CREATE TABLE `resource` (
    `id` int NOT NULL,
    `resource_name` varchar(100) NOT NULL,
    `title` varchar(40) DEFAULT NULL,
    `desc` varchar(255) DEFAULT NULL,
    `type` varchar(20) DEFAULT NULL,
    `create_date` datetime DEFAULT NULL,
    `create_by` varchar(255) DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    `update_by` varchar(255) DEFAULT NULL,
    `modify_counter` int DEFAULT NULL,
    `remark` varchar(255) DEFAULT NULL,
    `is_deleted` char(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;$


-- jpa.user_resource definition
DROP TABLE IF EXISTS `user_resource`;$
CREATE TABLE `user_resource` (
     `id` int NOT NULL,
     `user_id` varchar(50) NOT NULL,
     `resource_id` int NOT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;$

-- jpa.menu definition
DROP TABLE IF EXISTS `menu`;$
CREATE TABLE `menu` (
    `id` varchar(100) NOT NULL,
    `name` varchar(100) NOT NULL,
    `url` varchar(100) NOT NULL,
    `locked` char(1) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;$

-- ????????????????????????
-- ?????????????????????
DROP FUNCTION IF EXISTS seq_curval;$
$CREATE FUNCTION `seq_curval`(v_seq_name VARCHAR(100)) RETURNS INTEGER DETERMINISTIC
BEGIN
    DECLARE current INTEGER;
    SET current = 0;
    SELECT
        current_val INTO current
    FROM
        sequence
    WHERE
        seq_name = v_seq_name;
    RETURN current;
END;$

-- ????????????????????????
DROP FUNCTION IF EXISTS seq_nextval;$
$CREATE FUNCTION `seq_nextval`(v_seq_name VARCHAR(100)) RETURNS INTEGER DETERMINISTIC
BEGIN
    DECLARE current INTEGER;
    SET current = 0;
    SELECT
        current_val + increment_val INTO current
    FROM
        sequence
    WHERE
        seq_name = v_seq_name FOR UPDATE;
    UPDATE sequence
    SET current_val = current
    WHERE
        seq_name = v_seq_name;
    RETURN current;
END;$

-- ?????????????????????
DROP PROCEDURE IF EXISTS seq_setval;$
$CREATE PROCEDURE `seq_setval`(IN v_seq_name VARCHAR(100), IN val INTEGER)
BEGIN
    UPDATE jpa.sequence
        SET current_val = val
    WHERE seq_name = v_seq_name;
END;$

SET FOREIGN_KEY_CHECKS = 1;$
