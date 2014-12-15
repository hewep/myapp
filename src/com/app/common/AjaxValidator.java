package com.app.common;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.validate.Validator;

public abstract class AjaxValidator extends Validator {	
	
	private Map<String,String> errorMap = new HashMap<String,String>();
	private String lastMessage = "";
	
	@Override
	protected void addError(String errorKey, String errorMessage) {		
		errorMap.put(errorKey, errorMessage);
		lastMessage = errorMessage;
		super.addError(errorKey, errorMessage);		
	}
	
	protected Map<String,String> getErrorMap(){
		return errorMap;
	}
	
	protected String getLastMessage(){
		return lastMessage;
	}
}
