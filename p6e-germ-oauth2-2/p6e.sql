/*
 Navicat Premium Data Transfer
 Date: 14/12/2020 19:15:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p6e_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `p6e_oauth2_client`;
CREATE TABLE `p6e_oauth2_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'oauth2_id',
  `status` tinyint(2) NOT NULL DEFAULT '-1' COMMENT 'oauth2_客户端状态 -1 未激活 0 冻结 1 启动',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'oauth2_客户端名称',
  `key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'oauth2_客户端KEY',
  `secret` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'oauth2_客户端密钥',
  `scope` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ALL' COMMENT 'oauth2_客户端权限',
  `redirect_uri` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'oauth2_客户端重定向url',
  `describe` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2_客户端描述',
  `limiting_rule` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0,0,0,0' COMMENT 'oauth2_客户端限流规则',
  `icon` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for p6e_oauth2_log
-- ----------------------------
DROP TABLE IF EXISTS `p6e_oauth2_log`;
CREATE TABLE `p6e_oauth2_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'oauth2-日志主键自增',
  `uid` int(11) DEFAULT NULL COMMENT 'oauth2-日志用户ID',
  `cid` int(11) DEFAULT NULL COMMENT 'oauth2-日志客户端ID',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2-日志类型',
  `date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'oauth2-日志时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for p6e_oauth2_user
-- ----------------------------
DROP TABLE IF EXISTS `p6e_oauth2_user`;
CREATE TABLE `p6e_oauth2_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'oauth2-用户认证主键自增',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2-用户认证邮箱',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2-用户认证电话号码',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'oauth2-用户认证密码',
  `qq` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2-用户认证QQ认证',
  `wechat` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'oauth2-用户认证微信认证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
