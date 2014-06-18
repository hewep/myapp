-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.0.22-community-nt - MySQL Community Edition (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-06-18 22:57:05
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for myapp
CREATE DATABASE IF NOT EXISTS `myapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `myapp`;


-- Dumping structure for table myapp.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(10) NOT NULL auto_increment,
  `pid` int(10) default NULL COMMENT '父ID',
  `name` varchar(50) default NULL COMMENT '类别名称',
  `content` text COMMENT '类别内容',
  `type` varchar(10) default NULL COMMENT '类型',
  `topic_count` int(10) default NULL COMMENT '发贴数量',
  `remark` varchar(50) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.category: ~5 rows (approximately)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `pid`, `name`, `content`, `type`, `topic_count`, `remark`) VALUES
	(1, 0, '生活服务', 'www', '服务', NULL, NULL),
	(2, 0, '七嘴八舌', NULL, '交流', NULL, NULL),
	(3, 1, '食为天', NULL, NULL, NULL, NULL),
	(4, 2, '世界杯', NULL, NULL, NULL, NULL),
	(5, 1, '行无疆', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


-- Dumping structure for table myapp.reply
DROP TABLE IF EXISTS `reply`;
CREATE TABLE IF NOT EXISTS `reply` (
  `id` int(10) NOT NULL auto_increment,
  `pid` int(10) default NULL COMMENT '父ID',
  `user_id` int(10) default NULL COMMENT '用户ID',
  `topic_id` int(10) default NULL COMMENT '话题ID',
  `content` text COMMENT '回复内容',
  `remark` text COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.reply: ~0 rows (approximately)
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;


-- Dumping structure for table myapp.topic
DROP TABLE IF EXISTS `topic`;
CREATE TABLE IF NOT EXISTS `topic` (
  `id` int(10) NOT NULL auto_increment,
  `category_id` int(10) default NULL COMMENT '类别ID',
  `user_id` int(10) default NULL COMMENT '用户ID',
  `title` varchar(200) default NULL COMMENT '标题',
  `tags` varchar(200) default NULL COMMENT '标签',
  `content` text COMMENT '内容',
  `view_count` int(10) default NULL COMMENT '查看次数',
  `reply_count` int(10) default NULL COMMENT '回复数量',
  `create_time` datetime default NULL COMMENT '创建时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.topic: ~0 rows (approximately)
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` (`id`, `category_id`, `user_id`, `title`, `tags`, `content`, `view_count`, `reply_count`, `create_time`, `remark`) VALUES
	(1, 5, NULL, 'gg', NULL, 'kkk', NULL, NULL, '2014-06-18 00:00:00', NULL);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;


-- Dumping structure for table myapp.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL auto_increment,
  `user_name` varchar(50) default NULL COMMENT '昵称',
  `password` varchar(50) default NULL COMMENT '密码',
  `email` varchar(50) default NULL COMMENT '邮件',
  `register_time` datetime default NULL COMMENT '注册时间',
  `last_login_time` datetime default NULL COMMENT '最后登录时间',
  `login_count` int(10) default NULL COMMENT '登录次数',
  `pic_url` varchar(50) default NULL COMMENT '头像URL',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table myapp.user: ~1 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `register_time`, `last_login_time`, `login_count`, `pic_url`, `remark`) VALUES
	(1, 'hewep', 'hao123', 'hewephao@163.com', '2014-06-08 00:00:00', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
