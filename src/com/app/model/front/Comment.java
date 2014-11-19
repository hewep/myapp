package com.app.model.front;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * type (1:话题回复评论, 2: 消息评论)
 */
public class Comment extends BaseModel<Comment>{
	
	private static final long serialVersionUID = 7615180955823818222L;
	public static final Comment dao = new Comment();
	
	
	public Record getComment(int id){
		return Db.findFirst("select a.*, b.user_name as sender_name, c.user_name as receiver_name, "+
							"b.pic_url as sender_pic_path from comment a "+
							"left outer join user b on a.sender_id = b.id "+
							"left outer join user c on a.receiver_id = c.id "+
							"where a.id = ?", id);
	}
	public List<Record> findByTypeId(int typeId, int type){
		
		List<Record> records = Db.find("select a.*, b.user_name as sender_name, c.user_name as receiver_name,"+
								"b.pic_url as sender_pic_path from comment a "+
								"left outer join user b on a.sender_id = b.id "+
								"left outer join user c on a.receiver_id = c.id "+
								"where a.type_id = ? and a.type = ?", typeId, type);
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
