package com.app.controller;

import com.app.model.Topic;
import com.app.model.User;
import com.app.util.AjaxResult;
import com.app.util.DateUtils;

public class TopicController extends BaseController{
	
	public void addOrUpdate() throws Exception{
		AjaxResult result = new AjaxResult(1,"注册成功");
		try {
			User user = this.getCurrUser();
			if(user == null){
				result.setMsg(0, "请重新登录");
			}else{
				Topic topic = this.getModel(Topic.class).setAttrs(this.getParamMap());
				topic.set("create_time", DateUtils.getCurrDate());
				topic.set("user_id", user.get("id"));
				topic.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "提交失败："+e.getMessage());
		}finally{
			this.renderJson(result.toJson());
		}
	}
}
