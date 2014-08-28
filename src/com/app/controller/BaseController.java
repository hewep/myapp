package com.app.controller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.app.common.page.DataTablePage;
import com.app.model.admin.User;
import com.app.util.Const;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
/**
 * 控制器基类
 * @author Administrator
 *
 */
public class BaseController extends Controller{
	
	/******************  获取请求参数 *****************************/
	public Map<String, Object> getParamMap(){
		Map<String, Object> record = new HashMap<String, Object>();
		Map<String, String[]> map= this.getParaMap();
		Set<String> keys = map.keySet();
		for (String string : keys) {
			//if(map.get(string)[0] != null && !"".equals(map.get(string)[0])){
				record.put(string, map.get(string)[0]);
			//}
		}
		return record;
	}
	/***gson方式  json 转化为对象  ***/
	public <T> T fromJson(String json, Type type){
		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}
	
	/** 获取当前用户 **/
	public User getCurrUser(){
		Object obj = this.getSessionAttr(Const.CURRENT_USER);
		if(obj == null){
			return null;
		}else{
			return (User)obj;
		}
	}
	/**
	 * 用 BaseModel 中的方法
	 * Model 类型转化为 Record
	 * @param model
	 * @return Record
	 */
	public Record getRecordFromModel(Model<?> model){
		Record record = new Record();
		if(model != null){
			record.setColumns(CPI.getAttrs(model));
		}
		return record;
	}
	
}
