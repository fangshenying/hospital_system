package com.ipanel.web.kgbgsys.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipanel.web.kgbgsys.utils.MD5Util;

/**
 * Servlet Filter implementation class RequestFilter
 */
public class RequestFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RequestFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // ����������"http://xxx"�����������
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        // ������xxx���ڣ�����Ҫ�ٷ���Ԥ�������󣬿��Ի���ý��
        httpResponse.setHeader("Access-Control-Allow-Methods","POST, GET, DELETE, PUT");
        // ����������xxx����������
        httpResponse.setHeader("Access-Control-Max-Age","3628800");
        // ��������������������xxxͷ
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(request, response);
        
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
