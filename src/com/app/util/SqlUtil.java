package com.app.util;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;

/**
 * sql 条件拼接工具类
 * @author hewep
 */
public class SqlUtil {
    static Logger log = LoggerFactory.getLogger(SqlUtil.class);
    public static String EQUAL = " = ";
    public static String GREATER = "  >";
    public static String GREATER_OR_EQUAL = " >= ";
    public static String LESS = " < ";
    public static String LESS_OR_EQUAL = " <= ";
    public static String IN = " in ";
    
    private Controller controller;
    private String select;
    
    private List<String> andConditions = new ArrayList<String>();     // and 类型查询条件
    private List<String> orConditions = new ArrayList<String>();   // or 类型的 查询条件
    private List<String> otherConditions = new ArrayList<String>();// 其他类型查询条件
    
    public SqlUtil(){
    	
    }
    public SqlUtil(Controller controller){
    	this.controller = controller;
    }
    public SqlUtil(String select, Controller controller){
    	this.select = select;
    	this.controller = controller;
    }
    
    /**
     * 添加一个 查询条件
     * @param tableAlias 对应表的别名
     * @param queryType  查询类型 (<,>,=)
     * @param field	查询字段名称
     * @param value 查询值
     */
    public void addField(String conditionType,String tableAlias, String queryType, String field, String value){
    	if(StringKit.isBlank(value)){
    		return;
    	}
    	String key =  field;
		if(StringKit.notBlank(tableAlias)){
			key = tableAlias + "." + key;
		}
		if(queryType.equals(SqlUtil.IN)){
			value = "("+value+")";
		}
		
		String condition =  key + queryType + value;
    	
    	if(conditionType.equals("and")){
			andConditions.add(condition);
		}else if(conditionType.equals("or")){
			orConditions.add(condition);
		}
    }
    
    public void addAndField(String tableAlias, String queryType, String field, String value){
    	this.addField("and", tableAlias, queryType, field, value);
    }
    public void addOrField(String tableAlias, String queryType, String field, String value){
    	this.addField("or", tableAlias, queryType, field, value);
    }
    /**
     * 
     * @param tableAlias
     * @param queryType
     * @param fields
     */
    public void addAndFields(String tableAlias, String queryType, String... fields){
    	this.addFields("and", tableAlias, queryType, fields);
    }
    public void addOrFields(String tableAlias, String queryType, String... fields){
    	this.addFields("or", tableAlias, queryType, fields);
    }
    
    public void addFields(String conditionType, String tableAlias, String queryType, String... fields){
    	for (String field : fields) {
			String value = this.controller.getPara(field, "");
			this.addField(conditionType, tableAlias, queryType, field, value);
		}
    }
    
    public void like(String conditionType, String tableAlias, String field){
    	
    	String value = this.controller.getPara(field, "");
    	this.like(conditionType, tableAlias, field, value);
    	
    }
    public void like(String conditionType, String tableAlias, String field, String value){
    	if(StringKit.isBlank(value)){
    		return;
    	}
    	String key =  field;
		if(StringKit.notBlank(tableAlias)){
			key = tableAlias + "." + key;
		}
    	String condition =  key + " like "+ value;
    	
    	if(conditionType.equals("and")){
			andConditions.add(condition);
		}else if(conditionType.equals("or")){
			orConditions.add(condition);
		}
    }
    
    
    public void between(String conditionType, String tableAlias, String field, String value0, String value1){
    	if(!StringKit.notBlank(value0, value1)){
    		return;
    	}
    	
    	String key =  field;
		if(StringKit.notBlank(tableAlias)){
			key = tableAlias + "." + key;
		}
    	String condition = key + " between "+ value0 + " and " + value1;
    	
    	if(conditionType.equals("and")){
			andConditions.add(condition);
		}else if(conditionType.equals("or")){
			orConditions.add(condition);
		}
    }
    /**
     * 添加其他条件
     * @param condition
     */
    public void addContdition(String condition){
    	this.otherConditions.add(condition);
    }
    public String toWhere(){
    	StringBuilder sb = new StringBuilder("");
    	for (String condition : andConditions) {
			if(!sb.toString().equals("")){
				sb.append(" and ");
			}
			sb.append(condition);
		}
    	
    	for(String condition : orConditions){
    		if(!sb.toString().equals("")){
    			sb.append(" or ");
    		}
    		sb.append(condition);
    	}
    	
    	for(String condition : otherConditions){
    		if(sb.toString().equals("")){
    			sb.append(" 1=1 ");
    		}
    		sb.append(" ").append(condition);
    	}
    	
    	if(!sb.toString().equals("")){
    		return " where " + sb.toString();
    	}
    	return " where 1=1 ";
    }
    
    public String toSql(){
    	return select + this.toWhere();
    }
}
