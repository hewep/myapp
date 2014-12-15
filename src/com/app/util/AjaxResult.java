package com.app.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;

/**
 * ajax 结果类<br>
 * 状态  status  0:失败, 1:成功<br>
 * 消息  msg
 * 数据  data 
 * @author hewep
 */
public class AjaxResult {
	private Map<String,Object> result = new LinkedHashMap<String,Object>();
	
	public AjaxResult(int status) {		
		result.put("status", status);		
	}
	public AjaxResult(int status, String msg) {
		result.put("status", status);	
		result.put("msg", msg);			
	}
	public AjaxResult(int status, String type, String msg){
		result.put("status", status);	
		result.put("type", type);
		result.put("msg", msg);	
	}
	public void setStatus(int status){
		result.put("status", status);		
	}
	
	public void setFailure(String msg){
		result.put("status", 0);
		result.put("msg", msg);
	}
	public void setSuccess(String msg){
		result.put("status", 1);
		result.put("msg", msg);
	}
	public void setMsg(int status, String msg){
		result.put("status", status);	
		result.put("msg", msg);
	}
	public void setData(String name,Object value){
		result.put(name, value);
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
	/**
	 * 结果对象的 生成 json
	 * @return String
	 */
	public String toJson(){
		return JsonKit.mapToJson(result, 8);
	}
}

