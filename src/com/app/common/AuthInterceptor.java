package com.app.common;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.app.util.AjaxResult;
import com.app.util.Const;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.render.Render;


public class AuthInterceptor implements Interceptor{
	Logger logger = Logger.getLogger(this.getClass());
	
	private static String AUTH = ".*(add|del|edit|comment|reply).*";
	private Pattern pattern = Pattern.compile(AUTH, Pattern.CASE_INSENSITIVE);
	
	public void intercept(ActionInvocation ai){
		Controller controller = ai.getController();		
		HttpServletRequest request=controller.getRequest();
		controller.setAttr("baseUrl", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/");
		try {	
			String uri = request.getRequestURI();
			String method = uri.substring(uri.lastIndexOf("/"));
			if(pattern.matcher(method).matches() && controller.getSessionAttr(Const.CURRENT_USER) == null){
				
				AjaxResult result = new AjaxResult(0,Const.Type.NOT_LOGIN.getValue(),"用户尚未登录");
				controller.renderJson(result.toJson());
			}else{
				ai.invoke();			
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
}
