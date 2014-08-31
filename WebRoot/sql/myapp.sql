-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.0.22-community-nt - MySQL Community Edition (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-08-18 20:59:47
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
	(5, 1, '食为天', 'v', '服务', 3, NULL),
	(7, 1, '行无疆', NULL, '服务', NULL, NULL),
	(8, 1, '住哪儿', NULL, '服务', NULL, NULL),
	(9, 2, '世界杯', NULL, '交流', NULL, NULL),
	(10, 1, '测试1', NULL, NULL, NULL, NULL),
	(11, 1, '测试2', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;


-- Dumping structure for function myapp.check_table
DROP FUNCTION IF EXISTS `check_table`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `check_table`(`tablename` VARCHAR(50)) RETURNS tinyint(1)
BEGIN
    DECLARE t_count INT UNSIGNED;
    SELECT count(1) INTO t_count FROM information_schema.`TABLES` where TABLE_NAME=tablename and TABLE_SCHEMA='myapp';
    RETURN t_count>0;
END//
DELIMITER ;


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

-- Dumping data for table myapp.comment: ~8 rows (approximately)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`, `pid`, `reply_id`, `src_user_id`, `dest_user_id`, `content`, `create_time`) VALUES
	(1, 0, 1, 1, NULL, '密码', '2014-07-01 16:55:57'),
	(2, 1, 1, 1, 1, '版本', '2014-07-01 16:56:10'),
	(3, 1, 1, 3, 1, '哈哈', '2014-07-01 16:59:05'),
	(4, 1, 1, 3, 1, '是的', '2014-07-01 16:59:14'),
	(5, 0, 1, 3, NULL, '方法', '2014-07-01 16:59:55'),
	(6, 0, 13, 4, NULL, '订单', '2014-07-10 15:02:21'),
	(7, 6, 13, 4, 4, 'vv', '2014-07-10 15:03:15'),
	(8, 0, 13, 4, NULL, '查查', '2014-07-10 15:03:44');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;


-- Dumping structure for table myapp.menu
DROP TABLE IF EXISTS `menu`;
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(10) default NULL,
  `parent_id` int(10) default NULL,
  `name` varchar(50) default NULL,
  `title` varchar(50) default NULL,
  `url` varchar(100) default NULL,
  `icon` varchar(100) default NULL,
  `sort` int(11) default NULL,
  `level` int(11) default NULL,
  `remark` varchar(50) default NULL,
  showflag integer comment '显示标志(1:显示,2:不显示)',
  istreemenu integer comment '是否是菜单(1:菜单,2:操作按钮)',
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.menu: ~0 rows (approximately)
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;


-- Dumping structure for table myapp.menu_role
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE IF NOT EXISTS `menu_role` (
  `id` int(10) default NULL,
  `menu_id` int(10) default NULL,
  `role_id` int(10) default NULL,
  primary key(menu_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.menu_role: ~0 rows (approximately)
/*!40000 ALTER TABLE `menu_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu_role` ENABLE KEYS */;


-- Dumping structure for table myapp.message
DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(10) default NULL,
  `src_user_id` int(10) default NULL,
  `dest_user_id` int(10) default NULL,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.message: ~0 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;


-- Dumping structure for table myapp.permissions
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `title` varchar(100) default NULL,
  `code` varchar(100) default NULL,
  `url` varchar(100) default NULL,
  `icon` varchar(20) default NULL,
  `type` varchar(20) default NULL,
  `sort` int(11) default NULL,
  `remark` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.permissions: ~0 rows (approximately)
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;


-- Dumping structure for table myapp.permissions_role
DROP TABLE IF EXISTS `permissions_role`;
CREATE TABLE IF NOT EXISTS `permissions_role` (
  `id` int(10) default NULL,
  `perm_id` int(10) default NULL,
  `role_id` int(10) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.permissions_role: ~0 rows (approximately)
/*!40000 ALTER TABLE `permissions_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions_role` ENABLE KEYS */;


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

-- Dumping data for table myapp.reply: ~13 rows (approximately)
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`id`, `pid`, `user_id`, `topic_id`, `content`, `create_time`, `remark`) VALUES
	(1, 0, NULL, 1, '<pre class="prettyprint lang-java">public void test(){\n    System.out.println("hao");\n}</pre>\n<br class="Apple-interchange-newline" />', '2014-06-20 11:21:02', NULL),
	(2, 0, NULL, 1, '<pre class="prettyprint  lang-java">public void test2(){\n\n}</pre>', '2014-06-20 12:37:28', NULL),
	(3, 0, NULL, 1, '1111', '2014-06-27 14:44:22', NULL),
	(4, 0, NULL, 1, '123', '2014-06-27 14:55:44', NULL),
	(5, 0, NULL, 1, 'wwwww', '2014-06-27 14:57:07', NULL),
	(6, 0, 3, 7, 'III', '2014-07-01 17:01:14', NULL),
	(7, 0, 4, 8, '淡淡道', '2014-07-08 10:20:16', NULL),
	(8, 0, 4, 8, 'gg', '2014-07-08 10:36:34', NULL),
	(9, 0, 4, 8, 'bbb', '2014-07-08 10:37:15', NULL),
	(12, 0, 4, 7, 'vvv', '2014-07-08 10:54:08', NULL),
	(13, 0, 4, 9, '松岛枫是的', '2014-07-10 15:01:51', NULL),
	(14, 0, 4, 9, 'vv', '2014-07-10 16:48:46', NULL),
	(15, 0, 4, 9, '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/10.gif" border="0" alt="" />', '2014-07-11 09:29:14', NULL);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;


-- Dumping structure for table myapp.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(10) NOT NULL auto_increment COMMENT '主键',
  `name` varchar(20) default NULL COMMENT '角色名称',
  `code` varchar(20) default NULL COMMENT '角色代码',
  `sort` int(11) default NULL COMMENT '排序',
  `remark` varchar(100) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.role: ~0 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`, `code`, `sort`, `remark`) VALUES
	(1, '管理员', 'ADMIN', 100, '测试'),
	(2, 'nnn', NULL, NULL, NULL),
	(3, 'mm', NULL, NULL, NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


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

-- Dumping data for table myapp.topic: ~11 rows (approximately)
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` (`id`, `category_id`, `user_id`, `title`, `tags`, `content`, `view_count`, `reply_count`, `create_time`, `remark`) VALUES
	(1, 5, NULL, 'kk', NULL, '你奶奶', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(2, 5, NULL, 'qqq', NULL, 'mm', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(3, 7, NULL, 'mmm', 'ggg', 'sdfsdf', 1, NULL, '2014-06-26 00:00:00', NULL),
	(4, 7, 1, '新闻资讯', '哈哈', '的非官方的', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(5, 5, 1, 'kkk', '454', '789789798', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(6, 5, 1, '111', NULL, '45', 1, NULL, '2014-06-27 00:00:00', NULL),
	(7, 5, 1, '测试一下', NULL, '松岛枫松岛枫', 7, 1, '2014-07-01 14:30:24', NULL),
	(8, 5, 3, '密密麻麻的', NULL, '都是啥', 25, 1, '2014-07-03 15:35:50', NULL),
	(9, 5, 4, '会受到', NULL, '松岛枫松岛枫', 27, 3, '2014-07-09 14:59:00', NULL),
	(10, 5, 4, 'wewe', 'qqq', '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/66.gif" border="0" alt="" />', 6, NULL, '2014-07-11 09:28:12', NULL),
	(11, 5, 4, '123', '123', '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/10.gif" border="0" alt="" /><img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/12.gif" border="0" alt="" /><img src="http://api.map.baidu.com/staticimage?center=121.50015%2C31.234345&zoom=11&width=558&height=360&markers=121.50015%2C31.234345&markerStyles=l%2CA" alt="" />', 5, NULL, '2014-07-11 18:39:39', NULL);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;


-- Dumping structure for procedure myapp.update_db
DROP PROCEDURE IF EXISTS `update_db`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_db`()
BEGIN
	
    IF NOT CheckIsObjectExist('tbl_user_card') THEN
    	  select '2';
        CREATE TABLE `tbl_user_card` (
            `u_id` SMALLINT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
            `u_status` TINYINT(1) UNSIGNED DEFAULT '0',
            `u_name` VARCHAR(20) NOT NULL,
            PRIMARY KEY  (`u_id`)
        ) ENGINE=MyISAM DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
    END IF;

END//
DELIMITER ;


-- Dumping structure for table myapp.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL auto_increment COMMENT '主键ID',
  `user_name` varchar(50) default NULL COMMENT '昵称',
  `password` varchar(50) default NULL COMMENT '密码',
  `email` varchar(50) default NULL COMMENT '邮件',
  `gender` int(11) default NULL COMMENT '性别',
  `birthday` datetime default NULL COMMENT '生日',
  `phone` varchar(20) default NULL COMMENT '电话号码',
  `qq` varchar(20) default NULL COMMENT 'QQ',
  `province` varchar(20) default NULL COMMENT '省',
  `city` varchar(20) default NULL COMMENT '市',
  `district` varchar(20) default NULL COMMENT '区',
  `register_time` datetime default NULL COMMENT '注册时间',
  `last_login_time` datetime default NULL COMMENT '最后登录时间',
  `login_count` int(10) default NULL COMMENT '登录次数',
  `pic_url` varchar(50) default NULL COMMENT '头像URL',
  `type` varchar(50) default NULL COMMENT '类型',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table myapp.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `gender`, `birthday`, `phone`, `qq`, `province`, `city`, `district`, `register_time`, `last_login_time`, `login_count`, `pic_url`, `type`, `remark`) VALUES
	(8, 'hewep', '06a5ac9504e3a3c83574cf7a6479be2d', '553912407@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2014-08-13 00:00:00', NULL, NULL, NULL, NULL, NULL),
	(9, 'hh', '670b14728ad9902aecba32e22fa4f6bd', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2014-08-13 00:00:00', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table myapp.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(10) default NULL,
  `user_id` int(10) default NULL,
  `role_id` int(10) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.user_role: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
