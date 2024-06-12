# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20067
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 49.232.198.35 (MySQL 8.0.37)
# 数据库: bo_im
# 生成时间: 2024-05-31 14:19:30 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 t_friend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_friend`;

CREATE TABLE `t_friend` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `friend_id` bigint NOT NULL COMMENT '好友id',
  `friend_nick_name` varchar(255) NOT NULL COMMENT '好友昵称',
  `friend_head_image` varchar(255) DEFAULT '' COMMENT '好友头像',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='好友';



# 转储表 t_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_group`;

CREATE TABLE `t_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '群名字',
  `owner_id` bigint NOT NULL COMMENT '群主id',
  `head_image` varchar(255) DEFAULT '' COMMENT '群头像',
  `head_image_thumb` varchar(255) DEFAULT '' COMMENT '群头像缩略图',
  `notice` varchar(1024) DEFAULT '' COMMENT '群公告',
  `remark` varchar(255) DEFAULT '' COMMENT '群备注',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否已删除',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群';



# 转储表 t_group_member
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_group_member`;

CREATE TABLE `t_group_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint NOT NULL COMMENT '群id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `alias_name` varchar(255) DEFAULT '' COMMENT '组内显示名称',
  `head_image` varchar(255) DEFAULT '' COMMENT '用户头像',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `quit` tinyint(1) DEFAULT '0' COMMENT '是否已退出',
  `quit_time` datetime DEFAULT NULL COMMENT '退出时间',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群成员';



# 转储表 t_group_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_group_message`;

CREATE TABLE `t_group_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint NOT NULL COMMENT '群id',
  `send_id` bigint NOT NULL COMMENT '发送用户id',
  `send_nick_name` varchar(255) DEFAULT '' COMMENT '发送用户昵称',
  `recv_ids` varchar(1024) DEFAULT '' COMMENT '接收用户id,逗号分隔，为空表示发给所有成员',
  `content` text COMMENT '发送内容',
  `at_user_ids` varchar(1024) DEFAULT NULL COMMENT '被@的用户id列表，逗号分隔',
  `receipt` tinyint DEFAULT '0' COMMENT '是否回执消息',
  `receipt_ok` tinyint DEFAULT '0' COMMENT '回执消息是否完成',
  `type` tinyint(1) NOT NULL COMMENT '消息类型 0:文字 1:图片 2:文件 3:语音 4:视频 10:系统提示',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0:未发出 1:已送达  2:撤回 3:已读',
  `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群消息';



# 转储表 t_private_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_private_message`;

CREATE TABLE `t_private_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `send_id` bigint NOT NULL COMMENT '发送用户id',
  `recv_id` bigint NOT NULL COMMENT '接收用户id',
  `content` text COMMENT '发送内容',
  `type` tinyint(1) NOT NULL COMMENT '消息类型 0:文字 1:图片 2:文件 3:语音 10:系统提示',
  `status` tinyint(1) NOT NULL COMMENT '状态 0:未读 1:已读 2:撤回',
  `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_send_recv_id` (`send_id`,`recv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='私聊消息';



# 转储表 t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `nick_name` varchar(255) NOT NULL COMMENT '用户昵称',
  `head_image` varchar(255) DEFAULT '' COMMENT '用户头像',
  `head_image_thumb` varchar(255) DEFAULT '' COMMENT '用户头像缩略图',
  `password` varchar(255) NOT NULL COMMENT '密码(明文)',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别 0:男 1:女',
  `type` smallint DEFAULT '1' COMMENT '用户类型 1:普通用户 2:审核账户',
  `signature` varchar(1024) DEFAULT '' COMMENT '个性签名',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_name` (`user_name`),
  KEY `idx_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
