package com.app.front.controller;

import java.util.Map;

import com.app.common.BaseController;
import com.app.front.model.Blog;
import com.app.util.AjaxResult;
import com.app.util.Const;
import com.app.util.DateUtils;
import com.app.util.FileUtils;
import com.app.util.WebUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

public class BlogController extends BaseController{
	
	public void list(){
		
	}
	
	/**
	 * 根据 blog id 查找
	 */
	public void findById(){
				
		AjaxResult result = new AjaxResult(1);
		int blogId = this.getParaToInt("blog_id");
		result.setData("blog", Blog.dao.findById(blogId));
		this.renderJson(result.toJson());
	}
	
	public void deleteById(){
		AjaxResult result = new AjaxResult(1);
		int blogId = this.getParaToInt("blog_id");
		Blog.dao.deleteById(blogId);
		this.renderJson(result.toJson());
	}
	/**
	 * 根据类别查找 博客列表
	 */
	public void findByCateId(){
		AjaxResult result = new AjaxResult(1);
		int cateId = this.getParaToInt("cate_id", 0);
		result.setData("blogs", Blog.dao.findByCateId(cateId));
		this.renderJson(result.toJson());
	}
	public void addOrUpdate(){
		AjaxResult result = new AjaxResult(1,"操作成功");
		
		try {
			Blog blog = this.getModel(Blog.class).setAttrs(this.getParamMap());
			if(StrKit.notNull(blog.get("id"))){
				blog.update();
			}else{
				blog.set("user_id", this.getCurrUser().get("id"));
				blog.set("create_time", DateUtils.getCurrDateTime());
				blog.set("allow_comment", Blog.ALLOW_COMMENT_YES);
				blog.save();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(this.getClass().getName(), e);
			result.setFailure("保存失败");
		}
		this.renderJson(result.toJson());
	}
	
	public void upload(){
		AjaxResult result = new AjaxResult(1);
		String path = PathKit.getWebRootPath() + Const.BLOG_PATH;
		UploadFile uploadFile = this.getFile("imgFile", path);
		
		String url =  Const.BLOG_PATH+"/"+uploadFile.getFileName();
				
		result.setData("error", 0);
		result.setData("url", url);
		this.renderJson(result.toJson());
	}
}
