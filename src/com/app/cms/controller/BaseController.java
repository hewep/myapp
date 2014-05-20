package com.app.cms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
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
	
	/************************ Record 操作 *********************************/
	public double getDoubleValue(Record record, String field){
		double value = 0.0;
		Object obj = record.get(field);
		if(obj == null){
			return value;
		}
		if(obj instanceof Double){
			value = (Double)obj;
		}else if(obj instanceof String){
			if(StringKit.notBlank(obj.toString())){
				value = Double.parseDouble(obj.toString());
			}
		}
		return value;
	}
	public int getIntValue(Record record, String field){
		int value = 0;
		Object obj = record.get(field);
		if(obj == null){
			return value;
		}
		if(obj instanceof Integer){
			value = (Integer)obj;
		}else if(obj instanceof String){
			if(StringKit.notBlank(obj.toString())){
				value = Integer.parseInt(obj.toString());
			}
		}
		return value;
	}
	
	public int getSumValue(Record record, String... fields){
		int value = 0;
		for (String field : fields) {
			value += this.getIntValue(record, field);
		}
		return value;
	}
	
}
