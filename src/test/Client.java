package test;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

public class Client {
	enum Type{
		RED("red"),BULE("");
		
		Type(String value){
			
		}
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
	
}
