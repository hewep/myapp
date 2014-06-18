package com.app.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

public class Category extends Model<Category>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Category dao = new Category();
	
	public Map<String, Object> getAtts(){
		return this.getAttrs();
	}
}
