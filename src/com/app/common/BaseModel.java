package com.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 所有 model的基类
 * @author hewep
 * @param <M>
 */
public class BaseModel<M extends Model<M>> extends Model<M>{

	private static final long serialVersionUID = -4541089188014674024L;
	
	/**
	 * 转化Model的 Record类型
	 * @return  返回 record
	 */
	public Record getRecord(){
		Record record = new Record().setColumns(this.getAttrs());
		return record;
	}
	
	
	/**
	 * list 转化为树形结构
	 * @param list  转化对象
	 * @param id    字段名称
	 * @param pid   父级字段名称
	 * @param level 级联数
	 * @return 返回json格式的 数据
	 */
	public String  listToTree(List<M> list, String id, String pid, int level){
		Map<Integer, Record> map = new HashMap<Integer, Record>();
		for (M model: list) {
			map.put(model.getInt(id), new Record().setColumns(CPI.getAttrs(model)));
		}
		
		List<Record> records = new ArrayList<Record>();
		Record temp ;
		for (Record record : map.values()) {
			temp = map.get(record.getInt(pid));
			if(temp == null){
				records.add(record);
			}else{
				
				List<Record> subCates = temp.get("subCates");
				if(subCates == null){
					subCates = new ArrayList<Record>();
					temp.set("subCates", subCates);
				}
				subCates.add(record);
			}
		}
		return JsonKit.listToJson(records, level);
	}
	
}
