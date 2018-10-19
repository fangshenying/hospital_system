package com.ipanel.web.kgbgsys.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Conf {
	private static final String CONFIG_FILE = "/config.properties";
	private static Properties property = new Properties();
	
	//--ҽԺ�ӿ�ͬ����������
	public static String CRON_EXPRESSION_HOSPITAL;
	//--��ȡ��������б�ӿڵ�ַ
	public static String GET_DEPART_CATE_LIST;
	//--��ȡ��������µ����п��ҽӿڵ�ַ
	public static String GET_ALL_DEPART_LIST_OF_CATE;
	//--��ȡ�����µ�ҽ���б�ӿڵ�ַ
	public static String GET_ALL_DOCTOR_LIST_OF_DEPART;
	//--ҽԺ�ṩ��ͼƬǰ׺��ַ
	public static String HOSTPITAL_PIC_URL_PREFIX;
	
	//--���������ip
	public static String PROXY_IP;
	//--���������port
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
