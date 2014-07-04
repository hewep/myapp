-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.0.22-community-nt - MySQL Community Edition (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-07-04 18:34:28
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for myapp
DROP DATABASE IF EXISTS `myapp`;
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

-- Dumping data for table myapp.category: ~8 rows (approximately)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `pid`, `name`, `content`, `type`, `topic_count`, `remark`) VALUES
	(1, 0, '生活服务', 'hh', '服务', 0, NULL),
	(2, 0, '七嘴八舌', '66', '交流', NULL, '33'),
	(5, 1, '食为天', 'v', '服务', NULL, NULL),
	(7, 1, '行无疆', NULL, '服务', NULL, NULL),
	(8, 1, '住哪儿', NULL, '服务', NULL, NULL),
	(9, 2, '世界杯', NULL, '交流', NULL, NULL),
	(10, 1, '测试1', NULL, NULL, NULL, NULL),
	(11, 1, '测试2', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


-- Dumping structure for table myapp.comment
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(10) NOT NULL auto_increment COMMENT '评论ID',
  `pid` int(10) NOT NULL COMMENT '评论父Id',
  `reply_id` int(10) default NULL COMMENT '回复ID',
  `src_user_id` int(10) default NULL COMMENT '源用户ID',
  `dest_user_id` int(10) default NULL COMMENT '目标用户ID',
  `content` text COMMENT '评论内容',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.comment: ~5 rows (approximately)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`, `pid`, `reply_id`, `src_user_id`, `dest_user_id`, `content`, `create_time`) VALUES
	(1, 0, 1, 1, NULL, '密码', '2014-07-01 16:55:57'),
	(2, 1, 1, 1, 1, '版本', '2014-07-01 16:56:10'),
	(3, 1, 1, 3, 1, '哈哈', '2014-07-01 16:59:05'),
	(4, 1, 1, 3, 1, '是的', '2014-07-01 16:59:14'),
	(5, 0, 1, 3, NULL, '方法', '2014-07-01 16:59:55');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;


-- Dumping structure for table myapp.reply
DROP TABLE IF EXISTS `reply`;
CREATE TABLE IF NOT EXISTS `reply` (
  `id` int(10) NOT NULL auto_increment,
  `pid` int(10) default NULL COMMENT '父ID',
  `user_id` int(10) default NULL COMMENT '用户ID',
  `topic_id` int(10) default NULL COMMENT '话题ID',
  `content` text COMMENT '回复内容',
  `create_time` datetime default NULL COMMENT '回复时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.reply: ~6 rows (approximately)
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`id`, `pid`, `user_id`, `topic_id`, `content`, `create_time`, `remark`) VALUES
	(1, 0, NULL, 1, '<pre class="prettyprint lang-java">public void test(){\n    System.out.println("hao");\n}</pre>\n<br class="Apple-interchange-newline" />', '2014-06-20 11:21:02', NULL),
	(2, 0, NULL, 1, '<pre class="prettyprint  lang-java">public void test2(){\n\n}</pre>', '2014-06-20 12:37:28', NULL),
	(3, 0, NULL, 1, '1111', '2014-06-27 14:44:22', NULL),
	(4, 0, NULL, 1, '123', '2014-06-27 14:55:44', NULL),
	(5, 0, NULL, 1, 'wwwww', '2014-06-27 14:57:07', NULL),
	(6, 0, 3, 7, 'III', '2014-07-01 17:01:14', NULL);
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

-- Dumping data for table myapp.topic: ~8 rows (approximately)
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` (`id`, `category_id`, `user_id`, `title`, `tags`, `content`, `view_count`, `reply_count`, `create_time`, `remark`) VALUES
	(1, 5, NULL, 'kk', NULL, '你奶奶', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(2, 5, NULL, 'qqq', NULL, 'mm', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(3, 7, NULL, 'mmm', 'ggg', 'sdfsdf', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(4, 7, 1, '新闻资讯', '哈哈', '的非官方的', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(5, 5, 1, 'kkk', '454', '789789798', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(6, 5, 1, '111', NULL, '45', NULL, NULL, '2014-06-27 00:00:00', NULL),
	(7, 5, 1, '测试一下', NULL, '松岛枫松岛枫', NULL, NULL, '2014-07-01 14:30:24', NULL),
	(8, 5, 3, '密密麻麻的', NULL, '都是啥', NULL, NULL, '2014-07-03 15:35:50', NULL);
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
  `type` varchar(50) default NULL COMMENT '类型',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table myapp.user: ~1 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `register_time`, `last_login_time`, `login_count`, `pic_url`, `type`, `remark`) VALUES
	(4, 'hewep', '06a5ac9504e3a3c83574cf7a6479be2d', '553912407@qq.com', '2014-07-04 00:00:00', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
