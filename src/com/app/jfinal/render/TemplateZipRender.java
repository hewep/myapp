package com.app.jfinal.render;

import static com.jfinal.core.Const.DEFAULT_FILE_CONTENT_TYPE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.app.model.template.Template;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public class TemplateZipRender extends Render{
	private List<Template> templates;
	private String fileName;
	
	public TemplateZipRender(List<Template> templates, String fileName) {
		this.templates = templates;
		this.fileName = fileName;
	}
	
	public void render() {
		Enumeration<String> attrs = request.getAttributeNames();
		Map root = new HashMap();
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}
		response.reset();   // 清空response
		try {
			response.addHeader("Content-disposition", "attachment; filename=" + new String(this.fileName.getBytes("GBK"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			response.addHeader("Content-disposition", "attachment; filename=" + fileName);
		}
		
		String contentType = DEFAULT_FILE_CONTENT_TYPE;
        response.setContentType(contentType);
        
        // 自定义模板
		StringTemplateLoader strLoader = new StringTemplateLoader();
		for (Template template : templates) {
			String fileName = template.get("name")+"."+template.getStr("type");
			String fileContent = template.getStr("content");
			strLoader.putTemplate(fileName, fileContent);
		}
		
		OutputStream outputStream = null;
		
		try {
			outputStream = response.getOutputStream();
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream));
			
			Configuration config = new Configuration();
			config.setTemplateLoader(strLoader);
			
			for (Template template : templates) {
				String fileName = template.get("name")+"."+template.getStr("type");
				freemarker.template.Template templ = config.getTemplate(fileName, "utf-8");
				Writer out = new StringWriter();
				templ.process(root, out);
				
				byte[] buffer = out.toString().getBytes();
				
				ZipEntry entry = new ZipEntry(fileName);
				zos.putNextEntry(entry);
				zos.write(buffer, 0, buffer.length-1);
				
				out.flush();
			}
			zos.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RenderException(e);
		}finally{
			if (outputStream != null) {
            	try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
			
		}
	        
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4302879818227981713L;

}
