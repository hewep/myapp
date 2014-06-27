package com.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Reply extends Model<Reply>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Reply dao = new Reply();
	
	
	public List<Record> getComments(int replyId){
		
		List<Record> records = Db.find("select a.*, b.name as src_user_name, c.name as dest_user_name from comment a "+
										"left outer join user b on a.src_user_id = b.id "+
										"left outer join user c on a.dest_user_id = c.id "+
										" where reply_id = ? order by id", replyId);
		
		List<Record> comments = new ArrayList<Record>();
		for (Record record : comments) {
			
			if(record.getInt("pid") == 0){
				comments.add(record);
			}else{
				
			}
		}
		return comments;
	}
	
	private String getCateTree(List<Record> list){
		Map<Integer, Record> map = new HashMap<Integer, Record>();
		for (Record record : list) {
			map.put(record.getInt("id"), record);
		}
		
		List<Record> records = new ArrayList<Record>();
		Record temp ;
		for (Record record : map.values()) {
			temp = map.get(record.getInt("pid"));
			if(temp == null){
				records.add(record);
			}else{
				
				List<Record> subComments = temp.get("subComments");
				if(subComments == null){
					subComments = new ArrayList<Record>();
					temp.set("subComments", subComments);
				}
				subComments.add(record);
			}
		}
		return JsonKit.listToJson(records, 4);
	}
}
