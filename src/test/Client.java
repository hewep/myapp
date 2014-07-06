package test;


import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.app.util.Const;
import com.app.util.DateUtils;

public class Client {
	enum Type{
		RED("red"),BULE("");
		
		Type(String value){
			
		}
	}
	@Test
	public void testDate(){
		System.out.println(Const.ACOUNT_ITEMS.contains("topic/info"));
	}
	@Test
	public void testReg(){
		String reg = "\\.save.*";
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		System.out.println(pattern.matcher("savehaha").matches());
		
	}
	
}
