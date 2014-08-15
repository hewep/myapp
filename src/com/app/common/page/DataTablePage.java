package com.app.common.page;

import java.util.List;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

public class DataTablePage {
	private int draw;
	private int pages;
	private int start;
	private int end;
	private int length;
	private int recordsTotal;
	private int recordsFiltered;
	private List<?> data;
	
	public DataTablePage() {
		super();
	}
	public DataTablePage(Page<?> page){
		this.draw = page.getPageNumber();
		this.pages = page.getTotalPage();
		this.length = page.getPageSize();
		this.recordsTotal = page.getTotalRow();
		this.recordsFiltered = page.getTotalRow();
		this.setData(page.getList());
		this.start = (this.draw-1)*this.length;
		this.end = this.draw * this.length;
	}
	public DataTablePage(int page, int pages, int length, int recordsTotal,List<?> data) {
		super();
		this.draw = page;
		this.pages = pages;
		this.length = length;
		this.recordsTotal = recordsTotal;
		this.setData(data);
		this.start = (this.draw-1)*this.length;
		this.end = this.draw * this.length;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int page) {
		this.draw = page;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
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
