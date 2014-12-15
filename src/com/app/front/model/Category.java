package com.app.front.model;

import java.util.ArrayList;
import java.util.List;

import com.app.common.BaseModel;
import com.app.common.bean.ZtreeBean;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @author hewep
 */
public class Category extends BaseModel<Category>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Category dao = new Category();
	
	/**
	 * 获取 类别树形结构
	 * @param type   类别类型
	 * @return  返回json格式数据
	 */
	public String getCateTree(String type){
		
		List<Category> list = Category.dao.find("select *,IFNULL(topic_count, 0) as topic_count from category where type = ?", type);
		
		return this.listToTree(list, "id", "pid", 4);
	}
	public String getCateZtree(String type){
		List<ZtreeBean> beans = new ArrayList<ZtreeBean>();
		List<Record> list = Db.find("select id, pid, name from category where type = ?", type);
		for (Record record : list) {
			beans.add(new ZtreeBean(record.getInt("id").toString(), record.getInt("pid").toString(), record.getStr("name")));
		}
		return JsonKit.listToJson(beans, 4);
	}
	/**
	 * 根据类别名称更新 类别的 话题数量
	 * @param cateId 类别id
	 */
	public void updateTopicCount(Object cateId){
		
		Category category = Category.dao.findById(cateId);
		int topicCount = category.get("topic_count", 0);
		category.set("topic_count", ++topicCount);
		category.update();
	}
	
}
