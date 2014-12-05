-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.0.22-community-nt - MySQL Community Edition (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-12-05 17:52:09
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for myapp
DROP DATABASE IF EXISTS `myapp`;
CREATE DATABASE IF NOT EXISTS `myapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `myapp`;


-- Dumping structure for table myapp.blog
DROP TABLE IF EXISTS `blog`;
CREATE TABLE IF NOT EXISTS `blog` (
  `id` varchar(20) NOT NULL default '',
  `category_id` int(11) NOT NULL,
  `user_id` int(11) default NULL,
  `title` varchar(100) default NULL,
  `snippet` varchar(500) default NULL COMMENT '文章摘要',
  `content` text,
  `tags` varchar(500) default NULL,
  `create_time` datetime default NULL,
  `allow_comment` tinyint(4) default '1',
  `remark` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.blog: ~0 rows (approximately)
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;


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
	(5, 1, '食为天', 'v', '服务', 7, NULL),
	(7, 1, '行无疆', NULL, '服务', NULL, NULL),
	(8, 1, '住哪儿', NULL, '服务', NULL, NULL),
	(9, 2, '世界杯', NULL, '交流', NULL, NULL),
	(10, 1, '测试1', NULL, NULL, NULL, NULL),
	(11, 1, '测试2', NULL, NULL, NULL, NULL),
	(12, 0, '技术', '编程', 'blog', NULL, NULL),
	(13, 12, 'java', NULL, 'blog', NULL, NULL),
	(14, 12, '前端开发', NULL, 'blog', NULL, NULL),
	(15, 0, '关注', NULL, 'blog', NULL, NULL);
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
  `pid` int(10) default NULL COMMENT '评论父ID',
  `type_id` int(10) default NULL COMMENT '评论对象ID',
  `sender_id` int(10) default NULL COMMENT '发送者ID',
  `receiver_id` int(10) default NULL COMMENT '接受者ID',
  `content` text COMMENT '评论内容',
  `type` int(11) default NULL COMMENT '评论对象类型(1: 话题回复, 2: 消息)',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.comment: ~25 rows (approximately)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`, `pid`, `type_id`, `sender_id`, `receiver_id`, `content`, `type`, `create_time`) VALUES
	(1, 0, 1, 1, NULL, '密码', NULL, '2014-07-01 16:55:57'),
	(2, 1, 1, 1, 1, '版本', NULL, '2014-07-01 16:56:10'),
	(3, 1, 1, 3, 1, '哈哈', NULL, '2014-07-01 16:59:05'),
	(4, 1, 1, 3, 1, '是的', NULL, '2014-07-01 16:59:14'),
	(5, 0, 1, 3, NULL, '方法', NULL, '2014-07-01 16:59:55'),
	(6, 0, 13, 4, NULL, '订单', NULL, '2014-07-10 15:02:21'),
	(7, 6, 13, 4, 4, 'vv', NULL, '2014-07-10 15:03:15'),
	(8, 0, 13, 4, NULL, '查查', NULL, '2014-07-10 15:03:44'),
	(9, 0, 17, 8, NULL, 'hhhyh', NULL, '2014-11-10 19:59:13'),
	(10, 9, 17, 8, 8, 'ggg', NULL, '2014-11-10 20:00:32'),
	(11, 9, 17, 8, 8, 'yyy', NULL, '2014-11-10 20:00:37'),
	(12, NULL, 12, 8, 8, '密码', 2, '2014-11-14 15:12:57'),
	(13, NULL, 12, 8, 8, 'ddddddd', 2, '2014-11-14 16:14:18'),
	(14, NULL, 13, 8, 8, '222', 2, '2014-11-17 16:29:29'),
	(15, NULL, 13, 8, 8, '333', 2, '2014-11-17 16:35:54'),
	(16, NULL, 13, 8, 8, '444', 2, '2014-11-17 16:44:46'),
	(17, NULL, 13, 8, 8, '555', 2, '2014-11-17 16:46:50'),
	(18, NULL, 11, 8, 8, '', 2, '2014-11-17 17:44:07'),
	(19, 0, 17, 8, NULL, 'pp', 1, '2014-11-18 09:43:12'),
	(20, 19, 17, 8, NULL, '000', 1, '2014-11-18 09:43:24'),
	(21, 19, 17, 8, 8, 'kkk', 1, '2014-11-18 09:43:56'),
	(22, 19, 17, 8, NULL, 'jj', 1, '2014-11-18 09:45:13'),
	(23, 19, 17, 8, 8, '0000', 1, '2014-11-18 09:48:40'),
	(24, 0, 17, 8, NULL, 'gggg', 1, '2014-11-18 09:49:21'),
	(25, NULL, 10, 8, 12, '222', 2, '2014-11-19 09:52:39');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;


-- Dumping structure for table myapp.menu
DROP TABLE IF EXISTS `menu`;
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(10) NOT NULL default '0',
  `parent_id` int(10) default NULL,
  `name` varchar(50) default NULL,
  `title` varchar(50) default NULL,
  `url` varchar(100) default NULL,
  `icon` varchar(100) default NULL,
  `sort` int(11) default NULL,
  `level` int(11) default NULL,
  `remark` varchar(50) default NULL,
  `showflag` int(11) default NULL COMMENT '显示标志(1:显示,2:不显示)',
  `istreemenu` int(11) default NULL COMMENT '是否是菜单(1:菜单,2:操作按钮)',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.menu: ~2 rows (approximately)
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`id`, `parent_id`, `name`, `title`, `url`, `icon`, `sort`, `level`, `remark`, `showflag`, `istreemenu`) VALUES
	(1000, 0, '基本信息', '基本信息', NULL, NULL, NULL, 1, NULL, 1, 1),
	(1100, 0, '系统信息', '系统信息', NULL, NULL, NULL, 1, NULL, 1, 1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;


-- Dumping structure for table myapp.menu_role
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE IF NOT EXISTS `menu_role` (
  `id` int(10) NOT NULL auto_increment,
  `menu_id` int(10) NOT NULL default '0',
  `role_id` int(10) NOT NULL default '0',
  PRIMARY KEY  (`menu_id`,`role_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.menu_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `menu_role` DISABLE KEYS */;
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`) VALUES
	(1, 1000, 3);
/*!40000 ALTER TABLE `menu_role` ENABLE KEYS */;


-- Dumping structure for table myapp.message
DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(10) NOT NULL auto_increment,
  `sender_id` int(10) default NULL COMMENT '发送者id',
  `receiver_id` int(10) default NULL COMMENT '接受者id',
  `content` text,
  `msg_type` int(10) default NULL COMMENT '1:动态, 2: 留言',
  `create_time` datetime default NULL,
  `remark` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.message: ~15 rows (approximately)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` (`id`, `sender_id`, `receiver_id`, `content`, `msg_type`, `create_time`, `remark`) VALUES
	(1, 12, 10, 'BBB', 2, '2014-11-06 11:19:22', NULL),
	(2, 12, 10, 'kkkooop', 2, '2014-11-06 18:34:57', NULL),
	(3, 12, 10, 'oooppp', 2, '2014-11-07 14:54:13', NULL),
	(4, 12, 10, 'kkokoko', 2, '2014-11-07 14:54:21', NULL),
	(5, 12, NULL, 'www', 1, '2014-11-07 15:14:03', NULL),
	(6, 12, NULL, 'sdf', 1, '2014-11-07 16:19:22', NULL),
	(7, 12, NULL, '333', 1, '2014-11-07 16:20:30', NULL),
	(8, 12, NULL, 'oooo', 1, '2014-11-07 16:26:58', NULL),
	(9, 12, NULL, 'meee', 1, '2014-11-07 16:35:16', NULL),
	(10, 12, 8, 'kkk', 2, '2014-11-07 16:51:40', NULL),
	(11, 8, 10, 'love', 2, '2014-11-07 16:57:00', NULL),
	(12, 8, NULL, 'vvvv', 1, '2014-11-07 17:00:52', NULL),
	(13, 8, NULL, 'mmmmmm', 1, '2014-11-14 16:26:43', NULL),
	(14, 8, NULL, '那你呢', 1, '2014-11-19 16:00:53', NULL),
	(15, 8, NULL, '33333', 1, '2014-11-20 09:11:21', NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;


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

-- Dumping data for table myapp.reply: ~16 rows (approximately)
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
	(15, 0, 4, 9, '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/10.gif" border="0" alt="" />', '2014-07-11 09:29:14', NULL),
	(16, 0, 8, 11, '嘎嘎嘎嘎', '2014-09-29 15:58:54', NULL),
	(17, 0, 12, 15, 'dfd', '2014-11-06 09:54:54', NULL),
	(18, 0, 8, 15, '555', '2014-11-18 09:30:59', NULL);
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

-- Dumping data for table myapp.role: ~3 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`, `code`, `sort`, `remark`) VALUES
	(1, '管理员', 'ADMIN', 100, '测试'),
	(2, 'nnn', NULL, NULL, NULL),
	(3, 'mm', NULL, NULL, NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Dumping structure for table myapp.template
DROP TABLE IF EXISTS `template`;
CREATE TABLE IF NOT EXISTS `template` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(50) default '',
  `content` text,
  `type` varchar(50) default '',
  `created_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板';

-- Dumping data for table myapp.template: ~6 rows (approximately)
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
INSERT INTO `template` (`id`, `name`, `content`, `type`, `created_time`) VALUES
	(1, 'java_controller', '<#assign modelNameLower=modelName?uncap_first/>\npackage com.app.controller.template;\n\nimport com.app.common.page.DataTablePage;\nimport com.app.controller.BaseController;\nimport com.app.model.${parentDir}.${modelName};\nimport com.app.util.AjaxResult;\nimport com.jfinal.kit.StrKit;\nimport com.jfinal.plugin.activerecord.Page; \npublic class ${modelName}Controller extends BaseController{\n	public void list() throws Exception{\n		int draw = this.getParaToInt("draw",0); \n		int pageNumber = this.getParaToInt("pageNumber", 1);\n		int pageSize = this.getParaToInt("pageSize", 1);\n		Page<${modelName}> page = ${modelName}.dao.paginate(pageNumber, pageSize, "select *", "from ${modelNameLower}");\n		\n		this.renderJson(new DataTablePage(page,draw).toJson());\n		\n	}\n	\n	public void addOrUpdate(){\n		AjaxResult result = new AjaxResult(1,"添加成功");\n		try {\n			${modelName} ${modelNameLower}= this.getModel(${modelName}.class).setAttrs(this.getParamMap());\n			String ${modelNameLower}Id = ${modelNameLower}.get("id", "");\n			if(StrKit.notBlank(${modelNameLower}Id)){\n				${modelNameLower}.update();\n			}else{\n				${modelNameLower}.save();\n			}\n			\n		} catch (Exception e) {\n			e.printStackTrace();\n			result.setMsg(0, "添加失败："+e.getMessage());\n		}\n		this.renderJson(result.toJson());\n	}\n	\n	public void findById(){\n		AjaxResult result = new AjaxResult(1, "");\n		String ${modelNameLower}Id = this.getPara("${modelNameLower}_id", "");\n		${modelName} ${modelNameLower} = Template.dao.findById(${modelNameLower}Id);\n		result.setData("data", ${modelNameLower});\n		this.renderJson(result.toJson());\n	}\n	\n	public void delById() throws Exception{\n		AjaxResult result = new AjaxResult(1, "");\n		String ids = this.getPara("ids", "");\n		try {\n			int count = ${modelName}.dao.deleteByIds(ids);\n		} catch (Exception e) {\n			// TODO: handle exception\n			e.printStackTrace();\n			result.setFailure("删除失败:"+e.getMessage());\n		}\n		this.renderJson(result.toJson());\n	}\n}', 'java', NULL),
	(2, 'js_controller', '\'use strict\';\n/* Controllers */\ndefine( function(){\n	var ${modelName}ListCtrl = [\'$scope\', \'$http\',\'dataTable\', function($scope, $http, dataTable){\n		\n		$scope.${modelNameLower}s = {};\n		var option = {\n			"columnDefs":[{"orderable":false, "targets":[0]}],\n			"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],\n			"columns":[\n			      {"data":"id"},\n			      {"data":"name"},\n			      {"data":"type"},\n			      {"data":"created_time"},\n			      {"title":"操作", \n			       "className":"center",\n			       "data" : function(row, type, val, meta){\n			    	   return \'&lt;a class="btn btn-mini btn-warning" href="/admin#/${modelNameLower}_info?${modelNameLower}_id=\'+row.id+\'" &gt;\'+\n                       \'&lt;i class="icon-edit icon-white"&gt;&lt;/i&gt; 编辑&lt;/a&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;\';\n			       }\n			      }\n			]\n		};\n		\n		var params = {"check_box":true, "id":"${modelNameLower}_list", url:"${modelNameLower}/list"};\n		var table = dataTable.init(params,option);\n		\n		$scope.del = function(){\n			var values = dataTable.getCheckedValues($("#${modelNameLower}_list"),"${modelNameLower}_id");\n			if(values == ""){\n				alert("请选择要删除的记录");return;\n			}\n			\n			$http({method:\'post\',\n				  url:"${modelNameLower}/delById",\n				  params:{ids:values}\n			}).success(function(result){\n				if(result.status == 1){\n					table.ajax.reload();\n				}else{\n					alert(result.msg);\n				}\n			}).error(function(){\n				alert("网络连接失败");\n			});\n		};\n	}];\n	\n	var ${modelName}InfoCtrl = [\'$scope\', \'$http\',\'$routeParams\',\'$location\', function($scope, $http, $routeParams, $location){\n		$scope.${modelNameLower} = {};\n		\n		$http({method:\'post\',\n			  url:"${modelNameLower}/findById",\n			  params:{${modelNameLower}_id:$routeParams.${modelNameLower}_id}\n		}).success(function(result){\n			$scope.${modelNameLower} = result.data;\n		}).error(function(){\n			alert("网络连接失败");\n		});\n		\n		$scope.addOrUpdate = function(){\n			\n			$http({\n				  method:\'post\',\n				  url:"${modelNameLower}/addOrUpdate",\n				  params:$scope.${modelNameLower}\n			}).success(function(result){\n				if(result.status == 1){\n					$location.path("/${modelNameLower}_list");\n				}else{\n					alert(result.msg);\n				}\n			}).error(function(){\n				alert("网络连接失败");\n			});\n		};\n		\n	}];\n	\n	return {list : ${modelName}ListCtrl, info : ${modelName}InfoCtrl};\n});', 'javascript', NULL),
	(3, 'html_list', '<div class="row-fluid">\n	<div class="span12">\n		<div id="jCrumbs" class="breadCrumb module" >\n		    <ul>\n		        <li>\n		            <a href="#"><i class="icon-home"></i></a>\n		        </li>\n		        <li>\n		            <a href="#">${menuName} 管理</a>\n		        </li>\n		        <li>\n		            	${menuName}列表 \n		        </li>\n		    </ul>\n		</div>					  \n		\n		<div class="w-box">\n			<div class="w-box-header">\n				${menuName}列表\n				<div class="pull-right">\n					<a class="btn btn-mini btn-primary" href="/admin#/${modelNameLower}_info" >\n                            <i class="icon-plus icon-white"></i> 新增\n                    </a>\n                    \n                    <a class="btn btn-mini btn-danger" href="javascript:;" ng-click="del();" >\n                            <i class="icon-remove icon-white"></i> 删除\n                    </a>\n				</div>\n			</div>\n				<table id="template_list" class="table table-striped table-bordered table-hover dTableR">\n					<thead>\n						<tr>\n							<th></th>\n							<th class="optional">编号</th>\n							<th class="essential persist">名称</th>\n							<th class="optional">类型</th>\n							<th class="optional">创建时间</th>\n							<th></th>\n						</tr>\n					</thead>\n				</table>\n		</div>\n		\n	</div>\n</div>', 'html', NULL),
	(4, 'html_info', '<div class="row-fluid">\n	\n	<div class="w-box">\n		<div class="w-box-header">\n			${menuName}信息\n			<div class="pull-right">\n				<a class="btn btn-mini btn-gebo" href="/admin#/${modelNameLower}_list" >\n                           	 返回列表\n                   </a>\n			</div>\n		</div>\n		\n		<div class="w-box-content" style="padding-top:10px;">\n			<form class="form-horizontal"  novalidate="novalidate">\n				<input type="hidden" ng-model="${modelNameLower}.id">\n				<div class="row-fluid">\n					<div class="span6">\n						<div class="control-group">\n							<label class="control-label"> 名称: <span class="required">*</span>\n							</label>\n							<div class="controls">\n								<input type="text" class="span6 m-wrap" required\n									ng-model="template.name">\n							</div>\n						</div>\n					</div>\n					<div class="span6">\n						<div class="control-group">\n							<label class="control-label"> 类型: <span class="required">*</span>\n							</label>\n							<div class="controls">\n								<input type="text" class="span6 m-wrap" required\n									ng-model="template.type" >\n							</div>\n						</div>\n					</div>\n				</div>\n				\n			</form>\n				\n		</div>\n		\n		<div style="margin-top:10px;text-algin:right;">\n			<button class="btn btn-primary" ng-click="addOrUpdate();">提 交</button>\n		</div>\n	</div>\n		\n</div>', 'html', NULL),
	(5, 'java_model', 'package com.app.model.${parentDir};\n\nimport com.app.model.BaseModel;\nimport com.jfinal.plugin.activerecord.Db;\n\npublic class ${modelName} extends BaseModel<${modelName}>{\n\n	private static final long serialVersionUID = 186684748419693891L;\n	\n	public static final ${modelName} dao = new ${modelName}();\n	\n	public int deleteByIds(String ids){\n		return Db.update("delete from ${modelNameLower} where id in ("+ids+")");\n	}\n}', 'java', NULL),
	(7, 'test', 'public void ${template}', 'java', NULL);
/*!40000 ALTER TABLE `template` ENABLE KEYS */;


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

-- Dumping data for table myapp.topic: ~15 rows (approximately)
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` (`id`, `category_id`, `user_id`, `title`, `tags`, `content`, `view_count`, `reply_count`, `create_time`, `remark`) VALUES
	(1, 5, NULL, 'kk', NULL, '你奶奶', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(2, 5, NULL, 'qqq', NULL, 'mm', NULL, NULL, '2014-06-19 00:00:00', NULL),
	(3, 7, NULL, 'mmm', 'ggg', 'sdfsdf', 4, NULL, '2014-06-26 00:00:00', NULL),
	(4, 7, 1, '新闻资讯', '哈哈', '的非官方的', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(5, 5, 1, 'kkk', '454', '789789798', NULL, NULL, '2014-06-26 00:00:00', NULL),
	(6, 5, 1, '111', NULL, '45', 1, NULL, '2014-06-27 00:00:00', NULL),
	(7, 5, 1, '测试一下', NULL, '松岛枫松岛枫', 7, 1, '2014-07-01 14:30:24', NULL),
	(8, 5, 3, '密密麻麻的', NULL, '都是啥', 25, 1, '2014-07-03 15:35:50', NULL),
	(9, 5, 4, '会受到', NULL, '松岛枫松岛枫', 27, 3, '2014-07-09 14:59:00', NULL),
	(10, 5, 4, 'wewe', 'qqq', '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/66.gif" border="0" alt="" />', 15, NULL, '2014-07-11 09:28:12', NULL),
	(11, 5, 4, '123', '123', '<img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/10.gif" border="0" alt="" /><img src="http://localhost:82/res/plugins/kindeditor/plugins/emoticons/images/12.gif" border="0" alt="" /><img src="http://api.map.baidu.com/staticimage?center=121.50015%2C31.234345&zoom=11&width=558&height=360&markers=121.50015%2C31.234345&markerStyles=l%2CA" alt="" />', 16, 1, '2014-07-11 18:39:39', NULL),
	(12, 5, 8, 'ccc', 'ss', 'sdfsdfsdf', 3, NULL, '2014-10-31 10:48:59', NULL),
	(13, 5, 8, 'vvv', 's', 'sdf', 3, NULL, '2014-11-04 13:56:19', NULL),
	(14, 5, 10, 'nn', NULL, 'eee', NULL, NULL, '2014-11-04 15:30:12', NULL),
	(15, 5, 10, 'ffff', NULL, 'sss', 69, 2, '2014-11-04 15:35:06', NULL);
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
  `pic_url` varchar(100) default NULL COMMENT '头像URL',
  `type` varchar(50) default NULL COMMENT '类型',
  `remark` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table myapp.user: ~5 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `gender`, `birthday`, `phone`, `qq`, `province`, `city`, `district`, `register_time`, `last_login_time`, `login_count`, `pic_url`, `type`, `remark`) VALUES
	(8, 'hewep', '06a5ac9504e3a3c83574cf7a6479be2d', '553912407@qq.com', 1, NULL, '152333', NULL, '北京市', '市辖区', '东城区', '2014-08-13 00:00:00', NULL, NULL, '/upload/img/head_pic/2014-10-27/hewep/hewep_100.jpg?1414656057899', NULL, NULL),
	(9, 'hh', '670b14728ad9902aecba32e22fa4f6bd', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2014-08-13 00:00:00', NULL, NULL, '/res/media/image/comm/portrait.gif', NULL, NULL),
	(10, 'acton', '06a5ac9504e3a3c83574cf7a6479be2d', '55391240@qq.com', 0, NULL, NULL, NULL, '北京市', '市辖区', '东城区', '2014-11-04 00:00:00', NULL, NULL, '/res/media/image/comm/portrait.gif', NULL, NULL),
	(11, 'shich', '06a5ac9504e3a3c83574cf7a6479be2d', '55391207@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2014-11-04 00:00:00', NULL, NULL, '/res/media/image/comm/portrait.gif', NULL, NULL),
	(12, 'mmmmm', '06a5ac9504e3a3c83574cf7a6479be2d', '55392407@qq.com', 1, NULL, NULL, NULL, '', '', '', '2014-11-04 00:00:00', NULL, NULL, '/res/media/image/comm/portrait.gif', NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table myapp.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(10) NOT NULL auto_increment,
  `user_id` int(10) default NULL,
  `role_id` int(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table myapp.user_role: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES
	(1, 8, 1),
	(2, 8, 2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
