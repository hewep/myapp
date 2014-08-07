package com.app.controller.front;

import com.app.controller.BaseController;

public class MessageController extends BaseController{
	
	public void list(){
		int pageNumber = this.getParaToInt(0,1);
		int userId = this.getCurrUser().getInt("id");
	}
}
