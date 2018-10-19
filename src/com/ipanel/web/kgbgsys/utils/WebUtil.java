package com.ipanel.web.kgbgsys.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	
	public static String getRequestIP(HttpServletRequest request){
		String ip;
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;
	}

}
