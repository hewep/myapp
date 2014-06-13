package com.app.controller;

import com.app.model.User;
import com.app.util.AjaxResult;
import com.jfinal.kit.StrKit;


public class IndexController extends BaseController{
	
	public void login(){
		
	}
	
	public void register(){
		AjaxResult result = new AjaxResult(1,"注册成功");
		try {
			User user = this.getModel(User.class);
			user.save();
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure(0, "注册失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
		
	}
	
	public void admin(){
		String type = this.getPara("type","");
		if(type.equals("login")){
			this.render("dashboard.html");
		}else{
			
			this.render("index.html");
		}
	}
}
