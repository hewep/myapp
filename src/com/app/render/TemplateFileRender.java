package com.app.render;

import static com.jfinal.core.Const.DEFAULT_FILE_CONTENT_TYPE;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public class TemplateFileRender extends Render{
	private String fileName ;
	private String fileContent;
	
	public TemplateFileRender(String fileName, String fileContent) {
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	
	public void render() {
		Enumeration<String> attrs = request.getAttributeNames();
		Map root = new HashMap();
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}
		
		try {
			response.addHeader("Content-disposition", "attachment; filename=" + new String(this.fileName.getBytes("GBK"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			response.addHeader("Content-disposition", "attachment; filename=" + fileName);
		}
		
		String contentType = DEFAULT_FILE_CONTENT_TYPE;
        response.setContentType(contentType);
        
		StringTemplateLoader strLoader = new StringTemplateLoader();
		strLoader.putTemplate(this.fileName, fileContent);
		
		OutputStream outputStream = null;
		try {
			Configuration config = new Configuration();
			config.setTemplateLoader(strLoader);
			freemarker.template.Template template = config.getTemplate(this.fileName, "utf-8");
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
