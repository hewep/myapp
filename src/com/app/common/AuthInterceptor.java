package com.app.common;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.model.User;
import com.app.util.AjaxResult;
import com.app.util.AuthUtils;
import com.app.util.Const;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;

public class AuthInterceptor implements Interceptor{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
			logger.error(controller.getClass().getName(),e);
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
			
			AjaxResult result = new AjaxResult(0,Const.ResultType.NOT_LOGIN.getValue(),"用户尚未登录");
			controller.renderJson(result.toJson());
			return false;
		}
		return true;
	}
	// 自动登录
	private void autoLogin(Controller controller) throws UnsupportedEncodingException{
		User currentUser = controller.getSessionAttr(Const.CURRENT_USER);
		if(currentUser != null){		// 已登录则返回
			return;
		}
		String authToken = controller.getCookie(Const.AUTH_TOKEN,"");
		String[] token = AuthUtils.getToken(authToken);
		
		if(!StrKit.notBlank(new String[]{token[0], token[1]})){	 // 未登陆返回
			return ;
		}
		
		User user = User.dao.getByEmailAndPwd(token[0], token[1]);
		 
		if(user != null){
			controller.setCookie(Const.AUTH_TOKEN, authToken, Const.COOKIE_AGE);
			controller.getSession().setAttribute("online", new UserOnlineListener(user));
			controller.getSession().setAttribute(Const.CURRENT_USER, user);
		}
	}
}
