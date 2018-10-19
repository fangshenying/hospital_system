package com.ipanel.web.kgbgsys.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.ipanel.webapp.framework.util.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class UploadFileExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		response.setCharacterEncoding("UTF-8");
		if (viewName != null) {
			// JSON格式返回
			ModelAndView mv = new ModelAndView();
			/* 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */
			MappingJacksonJsonView view = new MappingJacksonJsonView();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("code", "1000001");
			attributes.put("msg", ex.getMessage());
			view.setAttributesMap(attributes);
			mv.setView(view);
			Log.d("异常:" + ex.getMessage(), ex);
			return mv;
		} else {
			return null;
		}
	}
}