package com.app.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.app.common.bean.ZtreeBean;
import com.app.common.page.DataTablePage;
import com.app.controller.BaseController;
import com.app.model.admin.Menu;
import com.app.model.admin.MenuRole;
import com.app.model.admin.Role;
import com.app.model.admin.User;
import com.app.model.admin.UserRole;
import com.app.util.AjaxResult;
import com.app.util.AuthUtils;
import com.app.util.Const;
import com.app.util.DateUtils;
import com.app.validator.admin.UserValidator;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class UserController extends BaseController{
	
	/*** 后台操作函数 ****/
	public void list() throws Exception{
		
		int draw = this.getParaToInt("draw",0); 
		int pageNumber = this.getParaToInt("pageNumber", 1);
		int pageSize = this.getParaToInt("pageSize", 1);
		Page<User> page = User.dao.paginate(pageNumber, pageSize, "select *", "from user");
		
		this.renderJson(new DataTablePage(page,draw).toJson());
		
	}
	
	@Before(UserValidator.class)
	public void addOrUpdate(){
		AjaxResult result = new AjaxResult(1,"注册成功");
		try {
			User user = this.getModel(User.class).setAttrs(this.getParamMap());
			String userId = user.get("id", "");
			if(StrKit.notBlank(userId)){
				user.update();
			}else{
				user.set("password", DigestUtils.md5Hex(Const.DEFAULT_PWD));
				user.set("register_time", DateUtils.getCurrDate());
				user.save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "注册失败："+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
	
	public void findById(){
		AjaxResult result = new AjaxResult(1, "");
		String userId = this.getPara("user_id", "");
		User user = User.dao.findById(userId);
		result.setData("data", user);
		this.renderJson(result.toJson());
	}
	
	public void delById() throws Exception{
		AjaxResult result = new AjaxResult(1, "");
		String ids = this.getPara("ids", "");
		try {
			int count = User.dao.deleteByIds(ids);
			System.out.println(count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setFailure("删除失败:"+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
	
	/**用户注册**/
	//@ClearInterceptor(ClearLayer.ALL)	// 跳过鉴权
	@Before(UserValidator.class)
	public void register() throws Exception{
		AjaxResult result = new AjaxResult(1,"注册成功");
		try {
			User user = this.getModel(User.class).setAttrs(this.getParamMap());
			user.set("password", DigestUtils.md5Hex(user.getStr("password")));
			user.set("register_time", DateUtils.getCurrDate());
			user.save();
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "注册失败："+e.getMessage());
			throw new Exception(e);
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	/*** 更新用户基本信息 , 修改密码 ***/
	public void updateUser(){
		AjaxResult result = new AjaxResult(1, "修改成功");
		try {
			User user = new User().setAttrs(this.getParamMap());
			user.update();
			this.getSession().setAttribute(Const.CURRENT_USER, user);
			this.setCookie(Const.AUTH_TOKEN, AuthUtils.getCookieAuthToken(this.getRequest(), user), Const.COOKIE_AGE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("修改失败");
		}
		this.renderJson(result.toJson());
	}
	public void updatePassword(){
		AjaxResult result = new AjaxResult(1, "修改成功");
		try {
			String oldPassword = this.getPara("oldPassword","");
			String newPassword = this.getPara("newPassword","");
			String rePassword = this.getPara("rePassword","");
			if(!StrKit.notBlank(new String[]{newPassword, rePassword}) || !newPassword.equals(rePassword)){
				result.setFailure("密码不能为空, 两次密码输入必须一致");
				return;
			}
			User user = this.getCurrUser();
			String password = user.getStr("password");
			if(password.equals(DigestUtils.md5Hex(oldPassword))){
				user.set("password", DigestUtils.md5Hex(newPassword));
				user.update();
			}else{
				result.setFailure("原密码输入错误!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("修改失败");
		} finally{
			this.renderJson(result.toJson());
		}
	}
	
	/*** 邮箱 和用户名唯一验证 ***/
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
	
	public void getByUserName(){
		
		AjaxResult result = new AjaxResult(1);
		String userName = this.getPara("user_name","");
		User user = User.dao.findFirst("select * from user where user_name = ?", userName);
		if(user == null){
			result.setData("exist", false);
		}else{
			result.setData("exist", true);
		}
		this.renderJson(result.toJson());
	}
	
	/**
	 * 
	 * @brief 查询用户的角色信息
	 *
	 * @author
	 *    - 2014年8月25日  moonlightbro  创建初始版本
	 *
	 */
	public void findGrantsByUserId() {

		AjaxResult result = new AjaxResult(1, "");
		String user_id = this.getPara("user_id", "");
		List<UserRole> ownList = null;
		List<Role> allMenuList = null;
		List<ZtreeBean> treeList = new ArrayList<ZtreeBean>();
		ZtreeBean treeBean = null;
		try {
			//获取当前角色的权限
			ownList = UserRole.dao.find("select * from user_role where user_id=?",user_id);
			//获取所有的权限
			allMenuList = Role.dao.find("select * from role");
			
			//拼接显示的权限
			if(ownList != null) {//ownList 不为空,则allMenuList也不为空
				for(Role menu : allMenuList) {
					treeBean = new ZtreeBean();
					treeBean.setId(menu.getInt("id")+"");
					treeBean.setpId("");
					treeBean.setName(menu.getStr("name"));
					treeBean.setOpen(true);
					boolean checked = isChecked(treeBean.getId(),ownList);
					treeBean.setChecked(checked);
					treeList.add(treeBean);
				}
				
			}  else if(allMenuList != null){//ownList为空,allMenuList不为空
				for(Role menu : allMenuList) {
					treeBean = new ZtreeBean();
					treeBean.setId(menu.getInt("id")+"");
					treeBean.setpId("");
					treeBean.setName(menu.getStr("name"));
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
			result.setFailure("查询失败:"+e.getMessage());
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
	private boolean isChecked(String id, List<UserRole> ownList) {
		boolean flag = false;
		if(id == null || "".equals(id) || ownList == null || ownList.size() < 1) {
			flag = false;
		}
		int menuId = Integer.valueOf(id);
		for(UserRole mr : ownList)  {
			if(menuId == mr.getInt("role_id")){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @brief 保存用户的角色信息
	 *
	 * @author
	 *    - 2014年8月25日  moonlightbro  创建初始版本
	 *
	 */
	public void saveGrant() {
		AjaxResult result = new AjaxResult(1, "");
		String userId = this.getPara("user_id","");
		String desc = this.getPara("desc","");
		String[] grants = desc.split(",");
		UserRole menuRoles = null;
		List<MenuRole> list = null;
		try {
			Db.update("delete from user_role where role_id=?",userId);
			for(String g : grants) {
				menuRoles = new UserRole();
				menuRoles.set("user_id", userId);
				menuRoles.set("role_id", g);
				
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
	
}
