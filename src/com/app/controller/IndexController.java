package com.app.controller;

import com.app.common.UserSessionListener;
import com.app.model.User;
import com.app.util.AjaxResult;


public class IndexController extends BaseController{
	
	public void login(){
		AjaxResult result = new AjaxResult(1,"登陆成功");
		try {
			User user0 = this.getModel(User.class);
					user0.setAttrs(this.getParamMap());
			User user = User.dao.findFirst("select * from user where email = ? and password = ?",
					new Object[]{user0.get("email"), user0.get("password")});
			if(user == null ){
				result.setMsg(0, "用户名或密码错误!");
			}else{
				this.getSession().setAttribute("hh", new UserSessionListener(user));
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
		this.renderJson(result.toJson());
	}
	
	public void admin(){
		String type = this.getPara("type","");
		if(type.equals("login")){
			this.render("dashboard.html");
		}else{
			this.render("login.html");
		}
	}
}
