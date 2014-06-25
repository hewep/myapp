package test;


import org.junit.Test;

public class Client {
	enum Type{
		RED("red"),BULE("");
		
		Type(String value){
			
		}
	}
	@Test
	public void testDate(){
			//System.out.println(DateUtils.g);
		System.out.println(Type.BULE);
	}
}
