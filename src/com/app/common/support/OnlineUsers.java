package com.app.common.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import com.app.model.admin.User;

/**
 * TODO 在线用户管理类.
 */
public class OnlineUsers {	
	private Map<String,HttpSession> activeSessions = Collections.synchronizedMap(new HashMap<String,HttpSession>());
	private Map<String,User> activeUsers = Collections.synchronizedMap(new HashMap<String,User>());
	
	public OnlineUsers(){
		this.activeUsers =  Collections.synchronizedMap(new HashMap<String,User>());	
	}
	public void addUserSession(HttpSession session){
		User user = (User)session.getAttribute("user");
		if(user != null){						
			this.activeUsers.put(session.getId(), user);	
			this.activeSessions.put(session.getId(), session);
		}
	}	
	public void removeUserSession(HttpSession session){
		User user = this.activeUsers.remove(session.getId());
		this.activeSessions.remove(session.getId());		
	}	
	public User getUserByUserName(String userName){
		Iterator it = this.activeUsers.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			User user = this.activeUsers.get(key);	
			if(user.getStr("user_name").equals(userName)){
				return user;
			}
		}
		return null;
	}
	public void removeUserById(int userId){
		Iterator it = this.activeUsers.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			User user = this.activeUsers.get(key);	
			if(user.getInt("id") == userId){
				HttpSession session = this.activeSessions.get(key);
				session.invalidate();				
				break;
			}
		}		
	}
	public void removeUserByName(String userName){
		Iterator it = this.activeUsers.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			User user = this.activeUsers.get(key);	
			if(user.getStr("user_name").equals(userName)){
				HttpSession session = this.activeSessions.get(key);
				session.invalidate();
				break;
			}
		}		
	}
	public String toString(){
		StringBuilder bf = new StringBuilder();
		Iterator it = this.activeUsers.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			User user = this.activeUsers.get(key);	
			bf.append(user.getStr("user_name") + "|"+user.getStr(""));
		}
		return bf.toString();
	}
	public Vector<User> getUserList(){
		Vector<User> activeUserList = new Vector<User>();
		Iterator it = this.activeUsers.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			User user = this.activeUsers.get(key);	
			activeUserList.add(user);
		}				
		return activeUserList;
	}	
}
