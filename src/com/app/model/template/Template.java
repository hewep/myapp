package com.app.model.template;

import com.app.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;

public class Template extends BaseModel<Template>{

	private static final long serialVersionUID = 186684748419693891L;
	
	public static final Template dao = new Template();
	
	public int deleteByIds(String ids){
		return Db.update("delete from template where id in ("+ids+")");
	}
}
