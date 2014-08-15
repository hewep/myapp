package com.app.common;

import com.app.controller.IndexController;
import com.app.controller.admin.UserController;
import com.app.controller.front.CategoryController;
import com.app.controller.front.TopicController;
import com.app.interceptor.AuthInterceptor;
import com.app.interceptor.CountInterceptor;
import com.app.model.admin.User;
import com.app.model.front.Category;
import com.app.model.front.Comment;
import com.app.model.front.Reply;
import com.app.model.front.Topic;
import com.app.plugin.update.UpdateDbPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
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
		
		UpdateDbPlugin udp = new UpdateDbPlugin(cp); // 数据库更新插件
		me.add(udp);
		
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
		JFinal.start("WebRoot", 82, "/", 5);
	}
}
