package com.app.controller.front;

import java.util.List;

import com.app.controller.BaseController;
import com.app.model.front.Category;
import com.app.model.front.Comment;
import com.app.model.front.Reply;
import com.app.model.front.Topic;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class TopicController extends BaseController{
	
	
	public void list(){
		
		AjaxResult result = new AjaxResult(1);
		int pageNumber = this.getParaToInt(0,1);
		int pageSize = this.getParaToInt("pageSize", 10);
		Page<Topic> topics = Topic.dao.paginate(pageNumber, pageSize, "select *", "from topic");
		
		result.setData("page", topics);
		this.renderJson(result.toJson());
	}
	
	
	/**
	 * 更加类别ID 查询 帖子
	 */
	public void findByCateId(){
		int pageNumber = this.getParaToInt(0,1);
		int categoryId = this.getParaToInt("category_id");
		
		Page<Record> topics = Topic.dao.paginateByCateId(pageNumber, 2, categoryId);
		
		this.renderJson(topics);
	}
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"操作成功");
		try {
			Topic topic = this.getModel(Topic.class).setAttrs(this.getParamMap());
			if(StrKit.notNull(topic.get("id"))){
				topic.update();
			}else{
				topic.set("create_time", DateUtils.getCurrDateTime());
				topic.set("user_id", this.getCurrUser().get("id"));
				topic.save();
				
				Category.dao.updateTopicCount(topic.get("category_id")); // 更新对应类别的话题数量
				
				result.setData("data", topic);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "提交失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	public void info(){
		AjaxResult result = new AjaxResult(1);
		String topicId = this.getPara("topic_id");
		Topic topic = Topic.dao.findById(topicId);
		List<Record> replies = topic.getReplies();
		
		// 更新查询次数
		Topic.dao.updateViewCount(topic);
		
		result.setData("topic", topic);
		result.setData("replies", replies);
		this.renderJson(result.toJson());
	}
	
	// 回复话题
	public void addReply() throws Exception{
		AjaxResult result = new AjaxResult(1,"回复成功");
		try {
			Reply reply = this.getModel(Reply.class).setAttrs(this.getParamMap());
			reply.set("create_time", DateUtils.getCurrDateTime());
			reply.set("user_id", this.getCurrUser().get("id"));
			reply.set("pid", 0);
			reply.save();
			// 更新回复次数
			Topic.dao.updateReplyCount(reply.get("topic_id"));
			
			result.setData("reply", reply);
			
		} catch (Exception e) {
			result.setFailure("回复失败:"+e.getMessage());
			throw new Exception(e);
		}finally{
			this.renderJson(result.toJson());
		}
	}
	//评论回复
	public void addComment(){
		AjaxResult result = new AjaxResult(1,"评论成功");
		try {
			Comment comment = this.getModel(Comment.class).setAttrs(this.getParamMap());
			comment.set("src_user_id", this.getCurrUser().get("id"));
			comment.set("create_time", DateUtils.getCurrDateTime());
			comment.save();
			result.setData("comment", Comment.dao.getComment(comment.getInt("id")));
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("评论失败:"+e.getMessage());
		}
		
		this.renderJson(result.toJson());
		
	}
}
