package com.app.cms.controller;


public class IndexController extends BaseController{
	
	public void index(){
		
		this.render("index.ftl");
	}

	public void login(){				
	}
	
	public void logout(){		
		this.redirect("/login");
	}
}
