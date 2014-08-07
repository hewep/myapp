package com.app.model.front;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Message extends Model<Message>{
	
	private static final long serialVersionUID = 7925448656579052520L;
	public static final Message dao = new Message();
	
	public List<Message> paginateByUserId(int userId){
		return Message.dao.find("select * from message where dest_user_id=?", userId);
	}
	
}
