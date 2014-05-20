package com.app.cms.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
@Before(Tx.class)
public class UserController extends BaseController{
	
	public void list() throws Exception{
		int pageSize = this.getCookieToInt("pageSize",10);
		String sql = "select a.id, a.user_name,a.role, b.name as agent_name,"+
					" a.real_name,a.sex, a.mobile, a.status";
		Page<Record> recordPage = Db.paginate(getParaToInt(0, 1), pageSize, sql, " from users a left outer join agent b on a.agent_id = b.id order by a.id desc");
		
		setAttr("recordPage",recordPage);
		setAttr("title","用户列表");
		render("user_list.ftl");
	}
	public void addOrUpdate() throws Exception{
		
	}
	
	public void findById(){
	
	}
	
	public void delById() throws Exception{

	}
	
}
