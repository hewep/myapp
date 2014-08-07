package com.app.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.app.util.Const;
import com.app.util.DateUtils;
import com.app.util.WebUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

public class CountInterceptor implements Interceptor{
	
	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		Controller controller = ai.getController();		
		HttpServletRequest request=controller.getRequest();
		
		this.accessSiteAcount(request);	// 访问站点统计
		
		ai.invoke();
		
	}
	
	//访问站点统计
	private void accessSiteAcount(HttpServletRequest request){
		String ip = WebUtil.getIpAddr(request);
		String sessionId = request.getRequestedSessionId();
		Object obj = CacheKit.get(Const.SITE_ACOUNT, ip);
		
		if(obj == null){			//ip 不存在, 则放入缓存
			Const.SITE_VIEW_ACOUNT ++;
			CacheKit.put(Const.SITE_ACOUNT, ip, sessionId + "#" +DateUtils.getCurrDateTime());
		
		}else{						// 相同ip 如果在2小时以后, 重新打开浏览器, 则 计数 +1
			String value = obj.toString();
			String oldSessionId = value.split("#")[0];
			String oldTime = value.split("#")[1];
			
			// 上次访问到现在的时间间隔
			long interval = DateUtils.getDiffMills(DateUtils.parseDateTime(oldTime), new Date());
			if(interval > Const.SITE_ACOUNT_INTERVAL && !oldSessionId.equals(sessionId)){
				Const.SITE_VIEW_ACOUNT ++;
				CacheKit.put(Const.SITE_ACOUNT, ip, sessionId + "#" +DateUtils.getCurrDateTime());
			}
		}
	}
	
}
