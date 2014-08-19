package com.app.controller.admin;

import com.app.common.page.DataTablePage;
import com.app.controller.BaseController;
import com.app.model.admin.Role;
import com.app.util.AjaxResult;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class RoleController extends BaseController{
	
	public void list() throws Exception{
		
		int draw = this.getParaToInt("draw",0); 
		int pageNumber = this.getParaToInt("pageNumber", 0);
		int pageSize = this.getParaToInt("pageSize", 1);
		Page<Role> page = Role.dao.paginate(pageNumber, pageSize, "select *", "from role");
		
		this.renderJson(new DataTablePage(page,draw).toJson());
		
	}
	
	public void addOrUpdate(){
		AjaxResult result = new AjaxResult(1,"添加成功");
		try {
			Role role = this.getModel(Role.class).setAttrs(this.getParamMap());
			String roleId = role.get("id", "");
			if(StrKit.notBlank(roleId)){
				role.update();
			}else{
				role.save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "添加失败："+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
	
	public void findById(){
		AjaxResult result = new AjaxResult(1, "");
		String roleId = this.getPara("role_id", "");
		Role role = Role.dao.findById(roleId);
		result.setData("data", role);
		this.renderJson(result.toJson());
	}
	
	public void delById() throws Exception{
		AjaxResult result = new AjaxResult(1, "");
		String ids = this.getPara("ids", "");
		try {
			int count = Role.dao.deleteByIds(ids);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setFailure("删除失败:"+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
}
