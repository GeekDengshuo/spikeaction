/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySQL8.0.18
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : spikeAction

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 30/05/2021 18:31:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for spikeAction
-- ----------------------------
DROP TABLE IF EXISTS `spikeAction`;
CREATE TABLE `spikeAction` (
  `spikeAction_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`spikeAction_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of spikeAction
-- ----------------------------
BEGIN;
INSERT INTO `spikeAction` VALUES (1000, '5000元秒杀iphone12', 100, '2021-05-10 15:31:53', '2021-05-12 15:31:53', '2021-05-10 15:31:53', 0);
INSERT INTO `spikeAction` VALUES (1001, '500元秒杀华为耳机', 100, '2021-05-10 15:31:53', '2021-05-12 15:31:53', '2021-05-10 15:31:53', 0);
INSERT INTO `spikeAction` VALUES (1002, '4000元秒杀小米11pro', 100, '2021-05-10 15:31:53', '2021-05-12 15:31:53', '2021-05-10 15:31:53', 0);
INSERT INTO `spikeAction` VALUES (1003, '200元秒杀小米台灯', 100, '2021-05-10 15:31:53', '2021-05-12 15:31:53', '2021-05-10 15:31:53', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品详情',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存,-1表示无限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Records of t_goods
-- ----------------------------
BEGIN;
INSERT INTO `t_goods` VALUES (1, 'iPhone12', 'iPhone12 256G 国行', '/img/iPhone12.png', 'iPhone12 256G 国行 4GB', 5999.00, 100);
INSERT INTO `t_goods` VALUES (2, 'iPhone12Pro', 'iPhone12 Pro 512G 国行', '/img/iPhone12pro.png', 'iPhone12 256G 国行 6GB', 8999.00, 100);
COMMIT;

-- ----------------------------
-- Table structure for t_goods_spike
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_spike`;
CREATE TABLE `t_goods_spike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `spike_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
  `goods_spike_stock` int(11) DEFAULT '0' COMMENT '秒杀库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

-- ----------------------------
-- Records of t_goods_spike
-- ----------------------------
BEGIN;
INSERT INTO `t_goods_spike` VALUES (1, 1, 1999.00, 9, '2021-05-30 10:50:30', '2021-06-18 12:00:00');
INSERT INTO `t_goods_spike` VALUES (2, 2, 2999.00, 10, '2021-06-18 08:00:00', '2021-06-18 12:00:00');
COMMIT;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_address_id` bigint(20) DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1-PC,2-Andriod,3-ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '0-新建未支付,1-已支付,2-已发货，3-已收货,4-已退款,5-完成',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------
BEGIN;
INSERT INTO `t_order` VALUES (12, 15083004900, 1, 0, 'iPhone12', 1, 1999.00, 1, 0, '2021-05-30 16:28:21', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_order_spike
-- ----------------------------
DROP TABLE IF EXISTS `t_order_spike`;
CREATE TABLE `t_order_spike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';

-- ----------------------------
-- Records of t_order_spike
-- ----------------------------
BEGIN;
INSERT INTO `t_order_spike` VALUES (12, 15083004900, 12, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID,手机号码',
  `nickname` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
  `slat` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `head` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  `login_count` int(12) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (15083004900, 'admin', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', NULL, NULL, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
