package com.app.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
	
	private static final long serialVersionUID = 1889564964554437164L;
	public static final User dao = new User();
	
	public User getByEmailAndPwd(String email, String pwd){
		User user = dao.findFirst("select * from user where email = ? and password = ?",
				new Object[]{email, pwd});
		return user;
	}
}
