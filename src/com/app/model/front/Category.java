package com.app.model.front;

import com.app.model.BaseModel;

public class Category extends BaseModel<Category>{
	
	private static final long serialVersionUID = -2998814222448990585L;
	public static final Category dao = new Category();
	
	/**
	 * 根据类别名称更新 类别的 话题数量
	 * @param cateId
	 */
	public void updateTopicCount(Object cateId){
		
		Category category = Category.dao.findById(cateId);
		int topicCount = category.get("topic_count", 0);
		category.set("topic_count", ++topicCount);
		category.update();
	}
}
