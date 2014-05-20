package com.app.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;

public class AjaxResult {
	private Map<String,Object> result = new LinkedHashMap<String,Object>();	
	public AjaxResult(int status) {		
		result.put("status", status);		
	}
	public AjaxResult(int status, String msg) {
		result.put("status", status);	
		result.put("msg", msg);			
	}
	
	public void setStatus(int status){
		result.put("status", status);		
	}
	
	public void setMsg(String info){
		result.put("info", info);	
	}
	public void setFailure(int status, String msg){
		result.put("status", status);	
		result.put("msg", msg);
	}
	public void setData(String name,Map<?,?> data) {
		result.put(name, data);
	}
	public void setData(String name,String value){
		result.put(name, value);
	}
	public void setData(String name,List<?> list){
		result.put(name, list);
	}
	public void setData(String name, String...arr ){
		result.put(name, arr);
	}
	public String toJson(){
		return JsonKit.mapToJson(result, 4);
	}
}

