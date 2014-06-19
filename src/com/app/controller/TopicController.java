package com.app.controller;

import com.app.model.Topic;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class TopicController extends BaseController{
	
	public void list(){
		int pageNumber = this.getParaToInt(0,1);
		int categoryId = this.getParaToInt("category_id");
		Page<Topic> topics = Topic.dao.paginate(pageNumber, 10, "select * ", "from topic where category_id = ?", categoryId);
		
		this.renderJson(topics.getList());
	}
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"操作成功");
		try {
			Topic topic = this.getModel(Topic.class).setAttrs(this.getParamMap());
			if(StrKit.notNull(topic.get("id"))){
				topic.update();
			}else{
				topic.set("create_time", DateUtils.getCurrDate());
				//topic.set("user_id", this.getCurrUser().get("id"));
				topic.save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "提交失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
	
	public void info(){
		
	}
}
