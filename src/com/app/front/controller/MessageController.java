package com.app.front.controller;

import com.app.common.BaseController;
import com.app.front.model.Message;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

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
		
		Record params = this.getParamMap("user_id", "type");
		
		int pageNumber = this.getParaToInt(0,1);
		Page<Message> messages = Message.dao.paginateByUserId(pageNumber, 2, params);
		
		result.setData("datas", messages);
		this.renderJson(result.toJson());
	}
	
	public void deleteMessage(){
		AjaxResult result = new AjaxResult(1);
		int msgId = this.getParaToInt("msg_id", 0);
		Message.dao.deleteById(msgId);
		this.renderJson(result.toJson());
	}
}
