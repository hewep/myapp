package com.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		List<Record> records = Db.find("select a.*, b.user_name as src_user_name, c.user_name as dest_user_name from comment a "+
										"left outer join user b on a.src_user_id = b.id "+
										"left outer join user c on a.dest_user_id = c.id "+
										" where reply_id = ? order by id", replyId);
		return this.getCommTree(records);
	}
	
	private List<Record> getCommTree(List<Record> list){
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
		
		
		// 按照 id 排序
		Collections.sort(records, this.getComparator());
		
		for (Record record : records) {
			List<Record> subComments = record.get("subComments");
			if(subComments != null){
				Collections.sort(subComments, this.getComparator());
			}
		}
		
		return records;
	}
	
	public  Comparator<Record> getComparator(){
		return new Comparator<Record>() {

			@Override
			public int compare(Record o1, Record o2) {
				// TODO Auto-generated method stub
				return o1.getInt("id").compareTo(o2.getInt("id"));
			}
		};
	}
}
