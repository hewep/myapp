package com.app.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Comment extends Model<Comment>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7615180955823818222L;
	public static final Comment dao = new Comment();
	
}
