package com.app.model;

import java.util.Date;
import java.util.List;

import com.app.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Topic extends Model<Topic>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Topic dao = new Topic();
	
	public Page<Record> paginateByCateId(int pageNumber, int pageSize, int categoryId){
		Page<Record> topics = Db.paginate(pageNumber, pageSize, "select a.*, b.user_name ", 
						"from topic a "+
						"left outer join user b on a.user_id = b.id where a.category_id = ?", categoryId);
		return topics;
	}
	
	public List<Record> getReplies(){
		List<Record> replies = Db.find("select * from reply where topic_id = ?", this.getInt("id"));
		
		for (Record record : replies) {
			List<Record> comments = Reply.dao.getComments(record.getInt("id"));
			record.set("comments", comments);
		}
		return replies;
	}
}
