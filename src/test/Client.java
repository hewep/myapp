package test;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class Client {
	enum Type{
		RED("red"),BULE("");
		
		Type(String value){
			
		}
	}
	@Test
	public void testPath(){
		System.out.println(PathKit.getWebRootPath()+"\\sql\\myapp.sql");
	}
	@Test
	public void testDate(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("hh", "kkkk");
		Map<String, String> map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("hh", "22");
		System.out.println(map.get("hh"));
		
	}
	@Test
	public void testReg(){
		String reg = "\\.save.*";
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		System.out.println(pattern.matcher("savehaha").matches());
		
	}
	@Test
	public void testMd5(){
//		System.out.println(DigestUtils.md5Hex("hao123"));
		
		try {
			Class cache = Integer.class.getDeclaredClasses()[0];
	        Field c = cache.getDeclaredField("cache");
	        c.setAccessible(true);
	        Integer[] array = (Integer[]) c.get(cache);
	        //array[132] = array[136];
	        
	        System.out.println(Arrays.toString(array));
	        System.out.printf("%d",2 + 2); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	@Test
	public void testXml(){
		String filePath = PathKit.getWebRootPath()+"\\sql\\myapp.xml";
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(filePath));
		    Element root = document.getRootElement();
		    
		    List<String> tableList = new ArrayList<String>();
		    List<String> dataList = new ArrayList<String>();
		    List<Element> tables = root.elements("table");
		    for (Element table : tables) {
		    	 
				String sql = table.element("sql").getTextTrim();
				String data = table.element("data").getTextTrim();
				if(StrKit.notBlank(sql)){
					tableList.add(sql);
				}
				if(StrKit.notBlank("data")){
					dataList.add(data);
				}
			}
		    C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost:3306/myapp","root","shich");
		    cp.start();
			ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
			arp.start();
			arp.setShowSql(true);
		    Db.batch(tableList, tableList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
}
