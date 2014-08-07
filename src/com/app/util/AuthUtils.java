package com.app.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.app.model.admin.User;

public class AuthUtils {
	/**
	 * 生成cookie令牌
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getCookieAuthToken(HttpServletRequest request, User user) throws UnsupportedEncodingException {
		String password = user.getStr("password");
		String ip = WebUtil.getIpAddr(request);
		String userAgent = request.getHeader("User-Agent");
		long date = new Date().getTime();

		StringBuffer token = new StringBuffer();// 用户名.#.时间戳.#.密码.#.USER_IP.#.USER_AGENT
		token.append(user.getStr("email")).append(".#.")
			 .append(date).append(".#.")
			 .append(password).append(".#.")
			 .append(ip).append(".#.")
			 .append(userAgent);
		
		byte[] tokenByte = token.toString().getBytes("UTF-8");
		
		String authToken = Base64.encodeBase64String(tokenByte);
		
		return authToken;
	}
	
	public static String[] getToken(String authToken) throws UnsupportedEncodingException{
		byte[] tokenByte = Base64.decodeBase64(authToken);
		String token = new String(tokenByte,"UTF-8");
		String[] temps = token.split(".#.");
		temps = Arrays.copyOf(temps, 5);
		String[] arr = new String[]{temps[0], temps[2]};
		return arr;
	}
	
}
