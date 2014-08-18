package com.app.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class BaseModel<M extends Model<M>> extends Model<M>{

	private static final long serialVersionUID = -4541089188014674024L;
	
	public Record getRecord(){
		Record record = new Record().setColumns(this.getAttrs());
		return record;
	}
}
