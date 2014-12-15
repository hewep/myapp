package com.app.common;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.admin.model.User;
import com.app.admin.model.template.Template;
import com.app.jfinal.render.TemplateFileRender;
import com.app.jfinal.render.TemplateZipRender;
import com.app.util.Const;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 控制器基类
 * @author Administrator
 *
 */
public class BaseController extends Controller{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*** render 模板文件 ****/
	public void renderTemplateFile(Template template){
		this.render(new TemplateFileRender(template));
	}
	public void renderTemplateZip(List<Template> templates, String fileName){
		this.render(new TemplateZipRender(templates, fileName));
	}
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
	
	public Record getParamMap(String... paramNames){
		Record record = new Record();
		for (String paramName : paramNames) {
			record.set(paramName, this.getPara(paramName));
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
	
}
