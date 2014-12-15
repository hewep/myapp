package com.app.jfinal.render;

import static com.jfinal.core.Const.DEFAULT_FILE_CONTENT_TYPE;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.app.admin.model.template.Template;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public class TemplateFileRender extends Render{
	private Template template;
	
	public TemplateFileRender(Template template) {
		this.template = template;
	}
	
	public void render() {
		// 获取请求参数
		Enumeration<String> attrs = request.getAttributeNames();
		Map root = new HashMap();
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}
		String fileName = this.template.get("name","temp")+"."+this.template.get("type", "txt");
		String fileContent = this.template.get("content", "");
		
		try {
			response.addHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			response.addHeader("Content-disposition", "attachment; filename=" + fileName);
		}
		
		String contentType = DEFAULT_FILE_CONTENT_TYPE;
        response.setContentType(contentType);
        
        // 自定义模板
		StringTemplateLoader strLoader = new StringTemplateLoader();
		strLoader.putTemplate(fileName, fileContent);
		
		OutputStream outputStream = null;
		try {
			Configuration config = new Configuration();
			config.setTemplateLoader(strLoader);
			freemarker.template.Template template = config.getTemplate(fileName, "utf-8");
			//Writer out = new OutputStreamWriter(new FileOutputStream("D:\\hah.text"));
			
			Writer out = new StringWriter(fileContent.length());
			template.process(root, out);
			
			byte[] buffer = out.toString().getBytes();
			out.close();
			
			response.setContentLength(buffer.length);
			// 写入 response 输出流
			outputStream = response.getOutputStream();
			outputStream.write(buffer, 0, buffer.length);
			outputStream.flush();
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
