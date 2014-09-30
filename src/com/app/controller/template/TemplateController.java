package com.app.controller.template;

import java.util.List;

import com.app.common.page.DataTablePage;
import com.app.controller.BaseController;
import com.app.model.template.Template;
import com.app.util.AjaxResult;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class TemplateController extends BaseController{
	public void list() throws Exception{
		int draw = this.getParaToInt("draw",0); 
		int pageNumber = this.getParaToInt("pageNumber", 1);
		int pageSize = this.getParaToInt("pageSize", 1);
		Page<Template> page = Template.dao.paginate(pageNumber, pageSize, "select *", "from template");
		
		this.renderJson(new DataTablePage(page,draw).toJson());
		
	}
	
	public void addOrUpdate(){
		AjaxResult result = new AjaxResult(1,"添加成功");
		try {
			Template template = this.getModel(Template.class).setAttrs(this.getParamMap());
			String templateId = template.get("id", "");
			if(StrKit.notBlank(templateId)){
				template.update();
			}else{
				template.save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(0, "添加失败："+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
	
	public void findById(){
		AjaxResult result = new AjaxResult(1, "");
		String templateId = this.getPara("template_id", "");
		Template template = Template.dao.findById(templateId);
		result.setData("data", template);
		this.renderJson(result.toJson());
	}
	
	public void delById() throws Exception{
		AjaxResult result = new AjaxResult(1, "");
		String ids = this.getPara("ids", "");
		try {
			int count = Template.dao.deleteByIds(ids);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setFailure("删除失败:"+e.getMessage());
		}
		this.renderJson(result.toJson());
	}
	
	public void generateCode() {
		String templateId = this.getPara("id", "");
		Template template = Template.dao.clear().findById(templateId);
		
		try {
			if(template !=null){
				this.setAttr("parentDir", "hewep");
				this.setAttr("modelName", "TestModel");
				this.renderTemplateFile(template);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void batchGenerate() {
		String ids = this.getPara("ids","");
		List<Template> templates = Template.dao.findByIds(ids);
		this.setAttrs(this.getParamMap());
		this.renderTemplateZip(templates, "temp.zip");
	}
}
