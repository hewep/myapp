package com.app.common.page;

import java.util.List;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

public class DataTablePage {
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	private List<?> data;
	
	public DataTablePage() {
		super();
	}
	public DataTablePage(Page<?> page, int draw){
		this.draw = draw;
		this.recordsTotal = page.getTotalRow();
		this.recordsFiltered = page.getTotalRow();
		this.setData(page.getList());
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int page) {
		this.draw = page;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsDisplay) {
		this.recordsFiltered = recordsDisplay;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
	public String toJson(){
		return JsonKit.toJson(this, 8);
	}
}
