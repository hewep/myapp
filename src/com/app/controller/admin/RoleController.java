package com.app.controller.admin;

import java.util.ArrayList;
import java.util.List;

import com.app.common.bean.ZtreeBean;
import com.app.common.page.DataTablePage;
import com.app.controller.BaseController;
import com.app.model.admin.Menu;
import com.app.model.admin.MenuRole;
import com.app.model.admin.Role;
import com.app.util.AjaxResult;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
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
	
	/**
	 * 
	 * @brief 保存角色的权限信息
	 *
	 * @author
	 *    - 2014年8月25日  moonlightbro  创建初始版本
	 *
	 */
	public void saveGrant() {
		AjaxResult result = new AjaxResult(1, "");
		String roleId = this.getPara("role_id","");
		String desc = this.getPara("desc","");
		String[] grants = desc.split(",");
		MenuRole menuRoles = null;
		List<MenuRole> list = null;
		try {
			Db.update("delete from menu_role where role_id=?",roleId);
			for(String g : grants) {
				menuRoles = new MenuRole();
				menuRoles.set("role_id", roleId);
				menuRoles.set("menu_id", g);
				
				if(list == null || list.size() < 1){
					menuRoles.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(2);
		}
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
	
	/**
	 * 
	 * @brief 查找指定角色的权限信息
	 *
	 * @author
	 *    - 2014年8月25日  moonlightbro  创建初始版本
	 *
	 * @throws Exception
	 */
	public void findGrantsByRoleId() throws Exception{
		AjaxResult result = new AjaxResult(1, "");
		String role_id = this.getPara("role_id", "");
		List<MenuRole> ownList = null;
		List<Menu> allMenuList = null;
		List<ZtreeBean> treeList = new ArrayList<ZtreeBean>();
		ZtreeBean treeBean = null;
		try {
			//获取当前角色的权限
			ownList = MenuRole.dao.find("select * from menu_role where role_id=?",role_id);
			//获取所有的权限
			allMenuList = Menu.dao.find("select * from menu");
			
			//拼接显示的权限
			if(ownList != null) {//ownList 不为空,则allMenuList也不为空
				for(Menu menu : allMenuList) {
					treeBean = new ZtreeBean();
					treeBean.setId(menu.getInt("id")+"");
					treeBean.setpId(menu.getInt("parent_id")+"");
					treeBean.setName(menu.getStr("title"));
					treeBean.setOpen(true);
					boolean checked = isChecked(treeBean.getId(),ownList);
					treeBean.setChecked(checked);
					treeList.add(treeBean);
				}
				
			}  else if(allMenuList != null){//ownList为空,allMenuList不为空
				for(Menu menu : allMenuList) {
					treeBean = new ZtreeBean();
					treeBean.setId(menu.getInt("id")+"");
					treeBean.setpId(menu.getInt("parent_id")+"");
					treeBean.setName(menu.getStr("title"));
					treeBean.setOpen(true);
					boolean checked = isChecked(treeBean.getId(),ownList);
					treeBean.setChecked(checked);
					treeList.add(treeBean);
				}
			} else {//都为空
				
			}
			result.setData("data", treeList);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("删除失败:"+e.getMessage());
		}
		this.renderJson(result.toJson());
		
		
		
	}

	/**
	 * @brief 判断复选框是否被选中(选中代表已经授过权限)
	 *
	 * @author
	 *    - 2014年8月24日  moonlightbro  创建初始版本
	 *
	 * @param id
	 * @param ownList
	 * @return true:选中,false:不选中
	 */
	private boolean isChecked(String id, List<MenuRole> ownList) {
		boolean flag = false;
		if(id == null || "".equals(id) || ownList == null || ownList.size() < 1) {
			flag = false;
		}
		int menuId = Integer.valueOf(id);
		for(MenuRole mr : ownList)  {
			if(menuId == mr.getInt("menu_id")){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
}
