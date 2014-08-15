package com.app.model.admin;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
	
	private static final long serialVersionUID = 1889564964554437164L;
	public static final User dao = new User();
	
	public User getByEmailAndPwd(String email, String pwd){
		User user = dao.findFirst("select * from user where (email = ? or user_name=?) and password = ?",
				new Object[]{email,email, pwd});
		return user;
	}
	
	public int deleteByIds(String ids){
		return Db.update("delete from user where id in ("+ids+")");
	}
}
