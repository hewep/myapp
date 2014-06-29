package com.app.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Comment extends Model<Comment>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7615180955823818222L;
	public static final Comment dao = new Comment();
	
	public Record getComment(int id){
		return Db.findFirst("select a.*, b.user_name as src_user_name, c.user_name as dest_user_name from comment a "+
							"left outer join user b on a.src_user_id = b.id "+
							"left outer join user c on a.dest_user_id = c.id "+
							"where a.id = ?", id);
	}
}
