package com.ipanel.web.kgbgsys.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Conf {
	private static final String CONFIG_FILE = "/config.properties";
	private static Properties property = new Properties();
	
	//--医院接口同步任务周期
	public static String CRON_EXPRESSION_HOSPITAL;
	//--获取科室类别列表接口地址
	public static String GET_DEPART_CATE_LIST;
	//--获取科室类别下的所有科室接口地址
	public static String GET_ALL_DEPART_LIST_OF_CATE;
	//--获取科室下的医生列表接口地址
	public static String GET_ALL_DOCTOR_LIST_OF_DEPART;
	//--医院提供的图片前缀地址
	public static String HOSTPITAL_PIC_URL_PREFIX;
	
	//--代理服务器ip
	public static String PROXY_IP;
	//--代理服务器port
	public static String PROXY_PORT;
	
	static {
		init();
	}

	public static void init() {
		InputStream ins = null;
		try {
			ins = Conf.class.getResourceAsStream(CONFIG_FILE);
			property.load(new InputStreamReader(ins, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();

			if (ins != null)
				try {
					ins.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		CRON_EXPRESSION_HOSPITAL = getProperty("cron_expression_hospital");
		GET_DEPART_CATE_LIST = getProperty("get_depart_cate_list");
		GET_ALL_DEPART_LIST_OF_CATE = getProperty("get_all_depart_list_of_cate");
		GET_ALL_DOCTOR_LIST_OF_DEPART = getProperty("get_all_doctor_list_of_depart");
		HOSTPITAL_PIC_URL_PREFIX = getProperty("hospital_pic_url_prefix");
		
		PROXY_IP = getProperty("proxy_ip");
		PROXY_PORT = getProperty("proxy_port");
		
	}

	public static String getProperty(String key) {
		String r = property.getProperty(key);
		if (r == null) {
			r = "";
		}
		return r;
	}
}
