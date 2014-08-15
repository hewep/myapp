package com.app.plugin.update;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.c3p0.C3p0Plugin;
/**
 * 此类中不打印sql语句, 因为 batch 对应的  conn 用 statement语句
 * jfinal中的 SqlReporter 只拦截了 preparedStatement方法
 * 
 * (1) statement 非预编译 适合执行 sql 语句极少相似的情况
 * (2) preparedStatement 预编译, 语句相似的 sql 执行效率高, 且 防止sql 注入
 * (3) SqlReporter 为动态代理类, 在获取 conn 时 根据 showSql 判断是否返回代理类
 * @author hewep
 */
public class UpdateDbPlugin implements IPlugin{
	C3p0Plugin cp ;
	ActiveRecordPlugin arp;
	
	public UpdateDbPlugin(C3p0Plugin cp){
		this.cp = cp;
		this.arp = new ActiveRecordPlugin("update_db",cp);
		this.cp.start();
		
		this.arp.setShowSql(true);
		this.arp.start();
	}
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		System.out.println("开始检测数据库更新....");
		try {
			String filePath = PathKit.getWebRootPath()+"\\sql\\myapp.xml";
			SAXReader saxReader = new SAXReader();
		    Document document = saxReader.read(new File(filePath));
		    Element root = document.getRootElement();
		    
		    List<String> tableList = new ArrayList<String>();
		    List<String> dataList = new ArrayList<String>();
		    List<Element> tables = root.elements("table");
		    
		    for (Element table : tables) {
		    	String type = table.attributeValue("type");// 没有 返回null
		    	
		    	if("create".equals(type)){			// 新建表
		    		String sql = table.element("sql").getTextTrim();
					String data = table.elementText("data");
					if(StrKit.notBlank(sql)){
						tableList.add(sql);
					}
					if(StrKit.notBlank("data")){
						String[] arr = data.split(";");
		    			for (String str : arr) {
							if(StrKit.notBlank(str)){
								dataList.add(str.trim()+";");
							}
						}
					}
					
					table.addAttribute("type", "create");
		    	}else if("alter".equals(type) || "delete".endsWith(type)){
		    		String sql = table.elementText(type);
		    		if(StrKit.notBlank(sql)){
		    			String[] arr = sql.split(";");
		    			for (String str : arr) {
							if(StrKit.notBlank(str)){
								tableList.add(str.trim()+";");
							}
						}
					}
		    		
		    		table.addAttribute("type", type);
		    	}
				
			}
		    
		    System.out.println("正在升级数据库....");
		    if(tableList.size()>0){
		    	Db.batch(tableList, tableList.size());	
		    }
		    if(dataList.size()>0){
		    	Db.batch(dataList, dataList.size());
		    }
		    
		   // OutputFormat format = new OutputFormat("    ", false);// 设置缩进为4个空格，并且另起一行为true
	        XMLWriter xmlWriter2 = new XMLWriter(
	                new FileOutputStream(filePath));
	        xmlWriter2.write(document);
		    System.out.println("数据库更新完成...");
		} catch (Exception e) {
			throw new RuntimeException("UpdateDbPlugin start error:"+e.getMessage());
		}
		
		//this.cp.stop();  // 数据源不能停止
		return true;
	}

	@Override
	public boolean stop() {
		// 更新xml文件
		
		this.arp.stop();
		return true;
	}
	
}
