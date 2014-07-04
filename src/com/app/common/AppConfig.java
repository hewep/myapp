package com.app.common;

import com.app.controller.CategoryController;
import com.app.controller.IndexController;
import com.app.controller.TopicController;
import com.app.controller.UserController;
import com.app.model.Category;
import com.app.model.Comment;
import com.app.model.Reply;
import com.app.model.Topic;
import com.app.model.User;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

public class AppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);			
		me.setViewType(ViewType.FREE_MARKER);
		me.setFreeMarkerViewExtension(".html");
		me.setBaseViewPath("/WEB-INF/view/");
	}
	
	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/user", UserController.class);
		me.add("/topic", TopicController.class);
		me.add("/category", CategoryController.class);
		
	}

	@Override
	public void configHandler(Handlers me) {
		//me.add(new UrlSkipHandler("/", false));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
		me.add(new CountInterceptor());
		me.add(new TxByRegex(".*(add|del|edit).*"));
	}

	@Override
	public void configPlugin(Plugins me) {			
		
		C3p0Plugin cp = new C3p0Plugin(loadPropertyFile("jdbc.properties"));
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		
		me.add(new EhCachePlugin()); 	// 配置缓存
		
		arp.addMapping("user", User.class);
		arp.addMapping("topic", Topic.class);
		arp.addMapping("reply", Reply.class);
		arp.addMapping("category", Category.class);
		arp.addMapping("comment", Comment.class);
		
		arp.setShowSql(true);
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 81, "/", 5);
	}
}
