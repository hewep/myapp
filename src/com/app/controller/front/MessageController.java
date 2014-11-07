package com.app.controller.front;

import com.app.controller.BaseController;
import com.app.model.front.Message;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.plugin.activerecord.Page;

public class MessageController extends BaseController{
	
	public void list(){
		int pageNumber = this.getParaToInt(0,1);
		int userId = this.getCurrUser().getInt("id");
	}
	
	public void addMessage(){
		AjaxResult result = new AjaxResult(1,"发表成功");
		try {
			Message message = this.getModel(Message.class).setAttrs(this.getParamMap());
			message.set("sender_id", this.getCurrUser().get("id"));
			message.set("create_time", DateUtils.getCurrDateTime());
			message.save();
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailure("发表失败:"+e.getMessage());
		}
		
		this.renderJson(result.toJson());
	}
	
	public void findByUserId(){
		AjaxResult result = new AjaxResult(1);
		int userId = this.getCurrUser().get("id");
		int type = this.getParaToInt("type", 1);
		int pageNumber = this.getParaToInt(0,1);
		Page<Message> messages = Message.dao.paginateByUserId(pageNumber, 2, userId, type);
		
		result.setData("datas", messages);
		this.renderJson(result.toJson());
	}
}
