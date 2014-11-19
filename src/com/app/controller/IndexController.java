package com.app.controller;

import org.apache.commons.codec.digest.DigestUtils;

import com.app.common.listener.UserOnlineListener;
import com.app.model.admin.User;
import com.app.util.AjaxResult;
import com.app.util.AuthUtils;
import com.app.util.Const;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


public class IndexController extends BaseController{
	
	public void index(){
		this.render("index.html");
	}
	
	public void admin(){
		String type = this.getPara("type","");
		//if(type.equals("login")){
			this.render("dashboard.html");
		//}else{
		//	this.render("login.html");
		//}
	}
	
	public void login(){
		AjaxResult result = new AjaxResult(1,"登陆成功");
		try {
			User user0 = this.getModel(User.class).setAttrs(this.getParamMap());
			User user = User.dao.getByEmailAndPwd(user0.getStr("email"), DigestUtils.md5Hex(user0.getStr("password")));
			if(user == null ){
				result.setMsg(0, "用户名或密码错误!");
			}else{
				this.getSession().setAttribute("online", new UserOnlineListener(user));
				this.getSession().setAttribute(Const.CURRENT_USER, user);
				this.setCookie(Const.AUTH_TOKEN, AuthUtils.getCookieAuthToken(this.getRequest(), user), Const.COOKIE_AGE);
				result.setData("user", user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "登陆失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	public void logout(){
		AjaxResult result = new AjaxResult(1);
		this.getSession().invalidate();
		this.removeCookie("auth_token");
		this.renderJson(result.toJson());
	}
	
	public void account(){
		AjaxResult result = new AjaxResult(1);
		Record record = new Record();
		record.set("site_view_count", Const.SITE_VIEW_ACOUNT);
		record.set("topic_count", Db.queryLong("select count(1) from topic"));
		record.set("user_count", Db.queryLong("select count(1) from user"));
		record.set("newer", Db.findFirst("select user_name,id as user_id from user order by id desc limit 1"));
		
		result.setData("info", record);
		this.renderJson(result.toJson());
	}
}
