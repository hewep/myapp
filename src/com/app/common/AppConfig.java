package com.app.common;

import com.app.controller.IndexController;
import com.app.controller.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

public class AppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);			
		me.setViewType(ViewType.FREE_MARKER);
		me.setFreeMarkerViewExtension(".ftl");
		me.setBaseViewPath("/WEB-INF/view/");
	}
	
	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
	}

	@Override
	public void configHandler(Handlers me) {
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new TxByRegex(".*add.*"));
		me.add(new TxByRegex(".*del.*"));
		me.add(new TxByRegex(".*edit.*"));
	}

	@Override
	public void configPlugin(Plugins me) {			
		
		C3p0Plugin cp = new C3p0Plugin(loadPropertyFile("jdbc.properties"));
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.setShowSql(true);
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 81, "/", 5);
	}
}
