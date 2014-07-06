package com.app.util;

import java.util.ArrayList;
import java.util.List;

public class Const {
	
	public static String ONLINE_USER_LIST = "online_user_list"; // 在线用户列表
	public static String CURRENT_USER = "current_user";	// 当前用户
	public static String AUTH_TOKEN = "auth_token";		// 用户登录秘钥
	
	public static Integer SITE_ACOUNT = 0;		// 站点统计
	public static Long ACOUNT_INTERVAL = 2*1000L;  //统计间隔
	public static List<String> ACOUNT_ITEMS = new ArrayList<String>();
	
	public static Integer COOKIE_AGE = 7*24*60*60;		// cookie保存时间, 单位秒
	public static String COOKIE_PATH = "/";
	
	public static String SYSTEM_AUTH_KEY = "ttJw6Oc4NEtwPf8CbmwLNQ==";
	
	static{
		ACOUNT_ITEMS.add("/topic/info");
	}
	
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
}
