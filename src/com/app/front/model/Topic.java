package com.app.front.model;

import java.util.List;

import com.app.util.Const;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Topic extends Model<Topic>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Topic dao = new Topic();
	
	public Page<Record> paginateBySearch(int pageNumber, int pageSize, String search){
		Page<Record> topics = Db.paginate(pageNumber, pageSize, 
						"select a.*,IFNULL(a.view_count, 0) as view_count, IFNULL(a.reply_count,0) as reply_count, b.user_name, b.pic_url ", 
						"from topic a "+
						"left outer join user b on a.user_id = b.id "+
						"where a.title like ? or a.content like ? order by a.create_time desc", "%"+search+"%", "%"+search+"%");
		
		for(Record topic :topics.getList()){
			if(!StrKit.notBlank(topic.getStr("pic_url"))){
				topic.set("pic_url", Const.HEAD_PIC_DEFAULT);
			}
		}
		return topics;
	}
	/**
	 * 根据 类别ID 获取topic分页数据
	 * 
	 * @param pageNumber 第几页
	 * @param pageSize	 每页条数
	 * @param categoryId 类别ID
	 * @return
	 */
	public Page<Record> paginateByCateId(int pageNumber, int pageSize, int categoryId){
		Page<Record> topics = Db.paginate(pageNumber, pageSize, 
						"select a.*,IFNULL(a.view_count, 0) as view_count, IFNULL(a.reply_count,0) as reply_count, b.user_name, b.pic_url ", 
						"from topic a "+
						"left outer join user b on a.user_id = b.id "+
						"where a.category_id = ? order by a.create_time desc", categoryId);
		
		for(Record topic :topics.getList()){
			if(!StrKit.notBlank(topic.getStr("pic_url"))){
				topic.set("pic_url", Const.HEAD_PIC_DEFAULT);
			}
		}
		return topics;
	}
	public Record getTopic(int topicId){
		Record record = Db.findFirst("select a.*,b.user_name,b.pic_url from topic a "+
										"inner join user b on a.user_id = b.id "+
										"where a.id = ?", topicId);
		return record;
	}
	/**
	 * 根据当前 话题 获取 回复, 需先获取当前话题对象
	 * @return
	 */
	public List<Record> getReplies(int topicId){
		List<Record> replies = Db.find("select r.*, u.user_name, u.pic_url from reply r "+
										"inner join user u on r.user_id = u.id where topic_id = ?", topicId);
		
		for (Record record : replies) {
			List<Record> comments = Comment.dao.findByTypeId(record.getInt("id"),1);
			record.set("comments", comments);
		}
		return replies;
		
	}
	
	/**
	 * 更新 话题 访问次数
	 * @param topicId
	 * @param count
	 */
	public void updateViewCount(int topicId){
		Topic topic = this.findById(topicId);
		int viewCount = topic.get("view_count", 0);
		topic.set("view_count", ++viewCount);
		topic.update();
	}
	
	/**
	 * 更新 回复 次数
	 * @param topicId
	 * @param count
	 */
	public void updateReplyCount(Object topicId){
		
		Topic topic = Topic.dao.findById(topicId);
		int replyCount = topic.get("reply_count", 0);
		topic.set("reply_count", ++replyCount);
		topic.update();
	}
}
