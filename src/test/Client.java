package test;


import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.app.util.DateUtils;

public class Client {
	enum Type{
		RED("red"),BULE("");
		
		Type(String value){
			
		}
	}
	@Test
	public void testDate(){
		//System.out.println(DateUtils.getDiffDate("2014-06-30 13:31:00"));
		System.out.println(DigestUtils.md5Hex("123456"));
	}
	@Test
	public void testReg(){
		String reg = "\\.save.*";
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		System.out.println(pattern.matcher("savehaha").matches());
		
	}
}
