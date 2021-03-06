package com.app.common;

import com.app.admin.controller.MenuController;
import com.app.admin.controller.RoleController;
import com.app.admin.controller.UserController;
import com.app.admin.controller.template.TemplateController;
import com.app.admin.model.Menu;
import com.app.admin.model.MenuRole;
import com.app.admin.model.Role;
import com.app.admin.model.User;
import com.app.admin.model.UserRole;
import com.app.admin.model.template.Template;
import com.app.front.controller.BlogController;
import com.app.front.controller.CategoryController;
import com.app.front.controller.CommentController;
import com.app.front.controller.MessageController;
import com.app.front.controller.TopicController;
import com.app.front.model.Blog;
import com.app.front.model.Category;
import com.app.front.model.Comment;
import com.app.front.model.Message;
import com.app.front.model.Reply;
import com.app.front.model.Topic;
import com.app.jfinal.interceptor.AuthInterceptor;
import com.app.jfinal.interceptor.CountInterceptor;
import com.app.jfinal.plugin.update.UpdateDbPlugin;
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
		me.add("/role", RoleController.class);
		me.add("/menu", MenuController.class);
		
		me.add("/template", TemplateController.class);
		me.add("/topic", TopicController.class);
		me.add("/category", CategoryController.class);
		me.add("/message", MessageController.class);
		me.add("/comment", CommentController.class);
		me.add("/blog", BlogController.class);
		
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
		arp.addMapping("role", Role.class);
		arp.addMapping("menu", Menu.class);
		
		arp.addMapping("topic", Topic.class);
		arp.addMapping("reply", Reply.class);
		arp.addMapping("category", Category.class);
		arp.addMapping("comment", Comment.class);
		arp.addMapping("menu_role", MenuRole.class);
		arp.addMapping("user_role", UserRole.class);
		arp.addMapping("template", Template.class);
		arp.addMapping("message", Message.class);
		arp.addMapping("blog", Blog.class);
		
		arp.setShowSql(true);
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 82, "/", 5);
	}
}
