package com.app.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.app.model.User;
import com.app.util.Const;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

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
	/*** json 转化为对象  ***/
	public <T> T fromJson(String json, Class<T> clazz){
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
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
	
}
