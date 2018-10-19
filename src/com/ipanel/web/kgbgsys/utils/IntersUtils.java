package com.ipanel.web.kgbgsys.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.ipanel.web.kgbgsys.departmentsinfo.pageModel.DepartmentListModel;
import com.ipanel.web.kgbgsys.departmentsinfo.pageModel.DepartmentTypeModel;
import com.ipanel.web.kgbgsys.expertinfo.pageModel.DepartmentDoctorModel;
import com.ipanel.webapp.framework.util.Log;

public class IntersUtils {
	private final static String TAG = "IntersUtils";

	/**
	 * 获取科室类别列表
	 * 
	 * @return DepartmentTypeModel
	 * @throws Exception
	 */
	public static DepartmentTypeModel getDepartCateList() throws Exception {
		String requestUlrStr = Conf.GET_DEPART_CATE_LIST;
		URL url = new URL(requestUlrStr);
		//--HttpClient httpClient = HttpClient.create(url);
		HttpClient httpClient = HttpClient.createPorxyClient(url,Conf.PROXY_IP,Integer.parseInt(Conf.PROXY_PORT));
		httpClient.setRequestMethod(HttpClient.HTTP_METHOD_GET);
		String respData = httpClient.execute();
		if ((respData == null) || ("".equals(respData))) {
			Log.i(TAG, "***getDepartCateList error:" + "远端接口出现错误，没有取到相应的数据");
			return null;
		}
		// --去除返回中可能存在的转义字符"\"
		String respDataFormate = respData.replaceAll("\\\\", "/");
		// --Log.i(TAG, "***getDepartCateList:" + requestUlrStr + ", respData:"
		// + respDataFormate);
		// --先用JSONObject过滤一下
		JSONObject obj = JSONObject.fromObject(respDataFormate);
		int ret = obj.optInt("statusCode");
		if (ret != 200) {
			// --没有获取到数据，提示非法获取
			Log.i(TAG, "***getDepartCateList:" + requestUlrStr + ", respData:"
					+ respDataFormate);
			return null;
		}
		// --通过验证后再用fastjson进行处理
		DepartmentTypeModel dtModel = JSON.parseObject(respDataFormate,
				DepartmentTypeModel.class);
		Log.i(TAG, "***getDepartCateList:" + requestUlrStr + ", statusCode:"
				+ dtModel.getStatusCode());
		return dtModel;
	}

	/**
	 * 获取科室类别下的所有科室
	 * 
	 * @param DepartKeys
	 *            科室标识
	 * @return DepartmentListModel
	 * @throws Exception
	 */
	public static DepartmentListModel getAllDepartListOfCate(String DepartKeys)
			throws Exception {
		String requestUlrStr = Conf.GET_ALL_DEPART_LIST_OF_CATE + "&id="
				+ DepartKeys;
		URL url = new URL(requestUlrStr);
		//--HttpClient httpClient = HttpClient.create(url);
		HttpClient httpClient = HttpClient.createPorxyClient(url,Conf.PROXY_IP,Integer.parseInt(Conf.PROXY_PORT));
		httpClient.setRequestMethod(HttpClient.HTTP_METHOD_GET);
		String respData = httpClient.execute();
		if ((respData == null) || ("".equals(respData))) {
			Log.i(TAG, "***getAllDepartListOfCate error:"
					+ "远端接口出现错误，没有取到相应的数据");
			return null;
		}
		// --去除返回中可能存在的转义字符"\"
		String respDataFormate = respData.replaceAll("\\\\", "/");
		// --Log.i(TAG, "***getAllDepartListOfCate:" + requestUlrStr +
		// ", respData:" + respDataFormate);
		// --先用JSONObject过滤一下
		JSONObject obj = JSONObject.fromObject(respDataFormate);
		int ret = obj.optInt("statusCode");
		if (ret != 200) {
			// --没有获取到数据，提示非法获取
			Log.i(TAG, "***getAllDepartListOfCate:" + requestUlrStr
					+ ", respData:" + respDataFormate);
			return null;
		}
		// --通过验证后再用fastjson进行处理
		DepartmentListModel dListModel = JSON.parseObject(respDataFormate,
				DepartmentListModel.class);
		Log.i(TAG, "***getAllDepartListOfCate:" + requestUlrStr
				+ ", statusCode:" + dListModel.getStatusCode());
		return dListModel;
	}

	/**
	 * 获取科室下的医生列表
	 * 
	 * @param departId
	 *            科室id
	 * @return DepartmentDoctorModel
	 * @throws Exception
	 */
	public static DepartmentDoctorModel getAllDoctorListOfDepart(String departId)
			throws Exception {
		String requestUlrStr = Conf.GET_ALL_DOCTOR_LIST_OF_DEPART + "&id="
				+ departId;
		URL url = new URL(requestUlrStr);
		//--HttpClient httpClient = HttpClient.create(url);
		HttpClient httpClient = HttpClient.createPorxyClient(url,Conf.PROXY_IP,Integer.parseInt(Conf.PROXY_PORT));
		httpClient.setRequestMethod(HttpClient.HTTP_METHOD_GET);
		String respData = httpClient.execute();
		if ((respData == null) || ("".equals(respData))) {
			Log.i(TAG, "***getAllDoctorListOfDepart error:"
					+ "远端接口出现错误，没有取到相应的数据");
			return null;
		}
		// --去除返回中可能存在的转义字符"\"
		String respDataFormate = respData.replaceAll("\\\\", "/");
		// --Log.i(TAG, "***getAllDoctorListOfDepart:" + requestUlrStr +
		// ", respData:" + respDataFormate);
		// --先用JSONObject过滤一下
		JSONObject obj = JSONObject.fromObject(respDataFormate);
		int ret = obj.optInt("statusCode");
		if (ret != 200) {
			// --没有获取到数据，提示非法获取
			Log.i(TAG, "***getAllDoctorListOfDepart:" + requestUlrStr
					+ ", respData:" + respDataFormate);
			return null;
		}
		// --通过验证后再用fastjson进行处理
		DepartmentDoctorModel dDoctorModel = JSON.parseObject(respDataFormate,
				DepartmentDoctorModel.class);
		Log.i(TAG, "***getAllDoctorListOfDepart:" + requestUlrStr
				+ ", statusCode:" + dDoctorModel.getStatusCode());
		return dDoctorModel;
	}

	
	/**
	 * 从远端下载图片到本地
	 * @param picUrl 远端图片url路径
	 * @param path  本地要保存的路径
	 * @throws Exception
	 */
	public static void downloadFile(String picUrl, String path) throws Exception {
		InputStream is = null;
		OutputStream os = null;
		try {
			URL url = new URL(picUrl);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(5000);
			is = con.getInputStream();
			byte[] bs = new byte[1024];
			int len;
			File sf = new File(path);
			os = new FileOutputStream(sf);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (IOException e) {
			Log.e(TAG, "downloadFile exception:" + e);
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		//--IntersUtils.getAllDoctorListOfDepart("91");
		
		IntersUtils.downloadFile("http://www.sxmh.net.cn/Upfile/Face/face_176.jpg", "C:/test/face_176.jpg");
	}

}
