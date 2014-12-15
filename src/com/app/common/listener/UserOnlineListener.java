package com.app.common.listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.app.admin.model.User;
import com.app.util.Const;

public class UserOnlineListener implements HttpSessionBindingListener,Serializable{
	
	private static final long serialVersionUID = -3315611425180791427L;
	
	private User user;
	public UserOnlineListener(){
		
	}
	public UserOnlineListener(User user){
		this.user = user;
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		
		List<User> onlineUsers = (List<User>)application.getAttribute(Const.ONLINE_USER_LIST);
		if(onlineUsers == null){
			onlineUsers = new ArrayList<User>();
			application.setAttribute(Const.ONLINE_USER_LIST, onlineUsers);
		}
		onlineUsers.add(this.user);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		List<User> onlineUsers = (List<User>)application.getAttribute(Const.ONLINE_USER_LIST);
		onlineUsers.remove(this.user);
	}

}
