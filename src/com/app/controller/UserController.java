package com.app.controller;

import com.app.model.User;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;

public class UserController extends BaseController{
	
	public void list() throws Exception{
		
	}
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"注册成功");
		try {
			User user0 = User.dao.findFirst("select * from user where email = ?", this.getPara("email",""));
			if(user0 == null){
				User user = this.getModel(User.class).setAttrs(this.getParamMap());
				user.set("register_time", DateUtils.getCurrDate());
				user.save();
			}else{
				result.setFailure(0, "此邮箱已注册");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure(0, "注册失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	public void findById(){
		
	}
	
	public void delById() throws Exception{

	}
	
	public void getByEmail(){
		
		AjaxResult result = new AjaxResult(1);
		String email = this.getPara("email","");
		User user = User.dao.findFirst("select * from user where email = ?", email);
		if(user == null){
			result.setData("exist", false);
		}else{
			result.setData("exist", true);
		}
		this.renderJson(result.toJson());
	}
	
}
