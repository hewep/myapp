package com.app.controller.admin;

import com.app.common.page.DataTablePage;
import com.app.controller.BaseController;
import com.app.model.admin.Menu;
import com.app.util.AjaxResult;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class MenuController extends BaseController{
	
	/**
	 * 
	 * @brief 方法功能简要说明     查询所有菜单列表
	 *
	 * @author
	 *    - 2014年8月19日  moonlightbro  创建初始版本
	 *
	 * @throws Exception
	 */
	public void list() throws Exception{
		int draw = this.getParaToInt("draw",0); 
		int pageNumber = this.getParaToInt("pageNumber", 1);
		int pageSize = this.getParaToInt("pageSize", 1);
		Page<Menu> page = Menu.dao.paginate(pageNumber, pageSize, "select *", "from menu");
		
		this.renderJson(new DataTablePage(page,draw).toJson());
		
	}
	
	/**
	 * 
	 * @brief 方法功能简要说明	根据ID查询菜单对象
	 *
	 * @author
	 *    - 2014年8月19日  moonlightbro  创建初始版本
	 *
	 * @throws Exception
	 */
	public void findById() throws Exception{
		AjaxResult result = new AjaxResult(1,"");
		String menuId = this.getPara("menu_id","");
		Menu menu = Menu.dao.findById(menuId);
		result.setData("data", menu);
		this.renderJson(result.toJson());
				
	}
	
	/**
	 * 
	 * @brief 方法功能简要说明	添加菜单
	 *
	 * @author
	 *    - 2014年8月19日  moonlightbro  创建初始版本
	 *
	 * @throws Exception
	 */
	public void addOrUpdate() {
		AjaxResult result = new AjaxResult(1,"菜单添加成功");
		
		try {
			
			Menu menu = this.getModel(Menu.class).setAttrs(this.getParamMap());
			menu.save();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "添加菜单失败"+e.getMessage());
		}
		
		this.renderJson(result.toJson());
	}
}
