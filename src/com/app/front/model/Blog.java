package com.app.front.model;

import java.util.List;

import com.app.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * allow_comment  (1:是, 0: 否)
 * @author Administrator
 *
 */
public class Blog extends BaseModel<Blog>{
	
	private static final long serialVersionUID = 613822483108524338L;
	public static final Blog dao = new Blog();
	
	public static int ALLOW_COMMENT_YES = 1;
	public static int ALLOW_COMMENT_NO = 0;
	
	public List<Record> findByCateId(int cateId){
		if(cateId > 0){
			return Db.find("select b.*, c.name as category_name from blog b left outer join category c on b.category_id = c.id where category_id = ?", cateId);
		}
		return Db.find("select * from blog");
	}
}
