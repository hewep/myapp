package com.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.model.Category;
import com.app.util.AjaxResult;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

public class CategoryController extends BaseController{
	
	public void list(){
		AjaxResult result = new AjaxResult(1);
		List<Category> list = Category.dao.find("select *,IFNULL(topic_count, 0) as topic_count from category");
		
		
		if(this.getPara("type","").equals("tree")){
			result.setData("data", this.getCateTree(list));
		}else{
			result.setData("data", list);
		}
		this.renderJson(result.toJson());
	}
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"操作成功");
		try {
			String category = this.getPara("category");
			Map<String, Object> map = this.fromJson(category, new HashMap<String, Object>().getClass());
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
	
	private String getCateTree(List<Category> list){
		Map<Integer, Record> map = new HashMap<Integer, Record>();
		for (Category category : list) {
			map.put(category.getInt("id"), this.getRecordFromModel(category));
		}
		
		List<Record> records = new ArrayList<Record>();
		Record temp ;
		for (Record record : map.values()) {
			temp = map.get(record.getInt("pid"));
			if(temp == null){
				records.add(record);
			}else{
				
				List<Record> subCates = temp.get("subCates");
				if(subCates == null){
					subCates = new ArrayList<Record>();
					temp.set("subCates", subCates);
				}
				subCates.add(record);
			}
		}
		return JsonKit.listToJson(records, 4);
	}
}
