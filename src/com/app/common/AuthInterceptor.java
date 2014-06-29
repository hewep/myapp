package com.app.common;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.app.model.User;
import com.app.util.AjaxResult;
import com.app.util.Const;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;


public class AuthInterceptor implements Interceptor{
	Logger logger = Logger.getLogger(this.getClass());
	
	private static String AUTH = ".*(add|del|edit|comment|reply).*";
	private Pattern pattern = Pattern.compile(AUTH, Pattern.CASE_INSENSITIVE);
	
	public void intercept(ActionInvocation ai){
		Controller controller = ai.getController();		
		HttpServletRequest request=controller.getRequest();
		controller.setAttr("baseUrl", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/");
		try {	
			
			
			this.autoLogin(controller);		// 自动登录
			
			if(this.anthUrl(controller, request)){  // 检测是否有权限访问
				ai.invoke();
			}
			
			User currentUser = controller.getSessionAttr(Const.CURRENT_USER);
			if(currentUser != null){		// 已登录则返回
				controller.setAttr("current_user", JsonKit.toJson(currentUser));
			}
				
		} catch (Exception e) {	
			// 统一异常处理
//			logger.error(controller.getClass().getName(),e);
//			Render render = controller.getRender();
//			if(render == null){
//				controller.renderFreeMarker("/WEB-INF/view/common/error.ftl");
//			}
		
		}
	}
	
	// 鉴权
	private boolean anthUrl(Controller controller, HttpServletRequest request){
		String uri = request.getRequestURI();
		String method = uri.substring(uri.lastIndexOf("/")+1);
		if(pattern.matcher(method).matches() && controller.getSessionAttr(Const.CURRENT_USER) == null){
			
			AjaxResult result = new AjaxResult(0,Const.Type.NOT_LOGIN.getValue(),"用户尚未登录");
			controller.renderJson(result.toJson());
			return false;
		}
		return true;
	}
	// 自动登录
	private void autoLogin(Controller controller){
		User currentUser = controller.getSessionAttr(Const.CURRENT_USER);
		if(currentUser != null){		// 已登录则返回
			return;
		}
		String email = controller.getCookie("email", "");
		String password = controller.getCookie("password","");
		if(!StrKit.notBlank(new String[]{email, password})){	// cookie不存在email 和password 返回
			return ;
		}
		
		User user = User.dao.getByEmailAndPwd(email, password);
		
		if(user != null){
			controller.setCookie("email", email, Const.COOKIE_AGE);
			controller.setCookie("password", password, Const.COOKIE_AGE);
			controller.getSession().setAttribute("online", new UserOnlineListener(user));
			controller.getSession().setAttribute(Const.CURRENT_USER, user);
		}
	}
}
