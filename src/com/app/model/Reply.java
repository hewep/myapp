package com.app.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Reply extends Model<Reply>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Reply dao = new Reply();
	
	
	public List<Comment> getComments(int replyId){
		return Comment.dao.find("select * from comment where reply_id = ?", replyId);
	}
}
