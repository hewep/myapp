package com.app.controller;

import java.util.List;

import com.app.model.Category;
import com.app.model.Topic;
import com.app.model.User;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.kit.JsonKit;

public class CategoryController extends BaseController{
	
	public void list(){
		AjaxResult result = new AjaxResult(1,"添加成功");
		List<Category> list = Category.dao.find("select * from category");
		result.setData("data", list);
		System.out.println(result.toJson());
		this.renderJson(result.toJson());
	}
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"添加成功");
		try {
			User user = this.getCurrUser();
			if(user == null){
				result.setFailure(0, "请重新登录");
			}else{
				Topic topic = this.getModel(Topic.class).setAttrs(this.getParamMap());
				topic.set("create_time", DateUtils.getCurrDate());
				topic.set("user_id", user.get("id"));
				topic.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure(0, "提交失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
}
