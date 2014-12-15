package com.app.front.controller;

import java.util.List;

import com.app.common.BaseController;
import com.app.front.model.Comment;
import com.app.front.model.Topic;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommentController extends BaseController{
	
	public void list(){
		
		AjaxResult result = new AjaxResult(1);
		int pageNumber = this.getParaToInt(0,1);
		int pageSize = this.getParaToInt("pageSize", 10);
		Page<Topic> topics = Topic.dao.paginate(pageNumber, pageSize, "select *", "from comment");
		
		result.setData("page", topics);
		this.renderJson(result.toJson());
	}
	
	//添加评论
	public void addComment(){
		AjaxResult result = new AjaxResult(1,"评论成功");
		try {
			Comment comment = this.getModel(Comment.class).setAttrs(this.getParamMap());
			comment.set("sender_id", this.getCurrUser().get("id"));
			comment.set("create_time", DateUtils.getCurrDateTime());
			comment.save();
			result.setData("comment", Comment.dao.getComment(comment.getInt("id")));
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("评论失败:"+e.getMessage());
		}
		
		this.renderJson(result.toJson());
	}
	
	// 获取消息评论
	public void findByTypeId(){
		
		AjaxResult result = new AjaxResult(1);
		int typeId = this.getParaToInt("type_id", 0);
		int type = this.getParaToInt("type", 1);
		List<Record> comments = Comment.dao.findByTypeId(typeId, type);
		result.setData("comments", comments);
		
		this.renderJson(result.toJson());
	}
}
