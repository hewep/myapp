package com.app.validator.admin;

import com.app.model.admin.User;
import com.app.util.AjaxResult;
import com.app.validator.AjaxValidator;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class UserValidator extends AjaxValidator{
	
	String methodName;
	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		methodName = this.getActionMethod().getName();
		this.setShortCircuit(true);
		
		this.validateRequiredString("user_name", "name_error", "昵称不能为空");
		
		if(methodName.equals("addOrUpdate")){
			this.validateRequiredString("gender", "gender_error", "请填写性别");
		}else if(methodName.equals("register")){
			this.validateEmail("email", "email_error", "邮箱格式错误");
		}
		
		User user = null;
		String userId = c.getPara("id", "");
		if(StrKit.notBlank(userId)){
			user = User.dao.findFirst("select * from user where (email = ? or user_name = ?) and id = ?", 
										new Object[]{c.getPara("email"),c.getPara("user_name"), userId});
		}else{
			user = User.dao.findFirst("select * from user where email = ? or user_name = ?", c.getPara("email",""),c.getPara("user_name"));
		}
		
		if(user!=null){
			this.addError("email_error", "此邮箱或用户名已注册!");
		}
		
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		
		AjaxResult result = new AjaxResult(0);
		result.setData("msg", this.getLastMessage());
		c.renderJson(result.toJson());
	}

}
