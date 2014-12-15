package com.app.front.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.common.BaseController;
import com.app.front.model.Category;
import com.app.util.AjaxResult;
import com.google.gson.reflect.TypeToken;
import com.jfinal.kit.StrKit;

public class CategoryController extends BaseController{
	
	/** 后台操作 ***/
	public void list(){
		AjaxResult result = new AjaxResult(1);
		
		if(this.getPara("type","").equals("tree")){
			result.setData("data", Category.dao.getCateTree("bbs"));
		}else{
			List<Category> list = Category.dao.find("select *,IFNULL(topic_count, 0) as topic_count from category");
			result.setData("data", list);
		}
		this.renderJson(result.toJson());
	}
	
	public void findCatesByType(){
		AjaxResult result = new AjaxResult(1);
		String type = this.getPara("type", "");
		result.setData("data", Category.dao.getCateZtree(type));
		this.renderJson(result.toJson());
	}
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"操作成功");
		try {
			String category = this.getPara("category");
			Map<String, Object> map = this.fromJson(category, new TypeToken<HashMap<String,Object>>(){}.getType());
			Category record = new Category().setAttrs(map);
			if(StrKit.notNull(record.get("id"))){
				record.update();
			}else{
				record.save();
				result.setData("data", record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "提交失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	public void deleteById(){
		AjaxResult result = new AjaxResult(1,"删除成功");
		try {
			Category.dao.deleteById(this.getPara("id"));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "删除失败");
		}
		
		this.renderJson(result.toJson());
	}
}
