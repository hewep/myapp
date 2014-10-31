package com.app.util;

public class Const {
	/**用户常量**/
	public static String DEFAULT_PWD = "000000";			// 默认登录密码
	public static String ONLINE_USER_LIST = "online_user_list"; // 在线用户列表
	public static String CURRENT_USER = "current_user";			// 当前用户
	public static String AUTH_TOKEN = "auth_token";				// 用户登录秘钥
	
	/**站点常量**/
	public static String SITE_ACOUNT = "site_acount"; 		  // 站点统计缓存名称
	public static Integer SITE_VIEW_ACOUNT = 0;				  // 站点访问统计
	public static Long SITE_ACOUNT_INTERVAL = 2*60*60*1000L;  //站点统计间隔, 单位毫秒
	
	/**话题常量**/
	public static String TOPIC_ACOUNT = "topic_acount"; 			// 话题统计缓存名称
	public static String TOPIC_VIEW_ACOUNT = "topic_view_acount";   //话题浏览次数
	public static String TOPIC_REPLY_ACOUNT = "topic_reply_acount"; //话题回复次数
	
	/**cookie常量**/
	public static Integer COOKIE_AGE = 7*24*60*60;		// cookie保存时间, 单位秒
	public static String COOKIE_PATH = "/";
	
	/**系统秘钥**/
	public static String SYSTEM_AUTH_KEY = "ttJw6Oc4NEtwPf8CbmwLNQ==";
	
	/**返回结果常量**/
	public enum ResultType{
		NOT_LOGIN("not_login");
		
		private final String value ;
		ResultType(String value){
			this.value = value;
		}
		public String getValue(){
			return value;
		}
	}
	/**会员 信息常量**/
	
	public static String HEAD_PIC_PATH = "/upload/img/head_pic";
	public static String HEAD_PIC_DEFAULT = "/res/media/image/comm/portrait.gif";
}
