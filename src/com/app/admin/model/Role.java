package com.app.admin.model;

import com.app.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;

public class Role extends BaseModel<Role>{

	private static final long serialVersionUID = 2019287150759757048L;
	public static final Role dao = new Role();
	
	public int deleteByIds(String ids){
		return Db.update("delete from role where id in ("+ids+")");
	}
}
