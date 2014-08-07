package test;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
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
}
