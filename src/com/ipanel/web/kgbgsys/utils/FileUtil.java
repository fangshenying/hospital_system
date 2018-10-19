package com.ipanel.web.kgbgsys.utils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.ipanel.web.kgbgsys.utils.FtpUtil.FtpServerBean;
import com.ipanel.webapp.framework.util.Log;


public class FileUtil {
	private final static String TAG = "FileUtil";
	
	/**
	 * 上传远端图片到指定的ftp服务器上（已做负载均衡处理）
	 * @param ftpFileName ftp上文件的名字
	 * @param remotePicUrl 远端图片的url地址
	 * @throws Exception
	 */
	public static void uploadRemoPicFileToFtpServer(String ftpFileName, String remotePicUrl) throws Exception{
		Log.i(TAG, "***uploadRemoPicFileToFtpServer begin");
		InputStream inputStream = null;
		try{
			//--获取远程图片的输入流对象
			URL url = new URL(remotePicUrl);
			//--URLConnection con = url.openConnection();
			//--
			InetSocketAddress address = new InetSocketAddress(Conf.PROXY_IP, Integer.parseInt(Conf.PROXY_PORT));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			URLConnection con = url.openConnection(proxy);
			//--
			con.setConnectTimeout(5000);
			inputStream = con.getInputStream();
			
			//--将输入流传入ftp中
			FtpUtil ftpUtil = new FtpUtil();
			List<FtpServerBean> serverList = FtpUtil.getFtpServerList();
			for (FtpServerBean server : serverList) {
				boolean result = ftpUtil.ftpLogin(server);
				if (!result)
					throw new Exception(
							"loginning ftp server is failure,address:"
									+ server.getAddress() + " port:"
									+ server.getPort());
				//--将路径和文件分开
//				int indexId = ftpFileName.lastIndexOf("/");
//				
//				result = ftpUtil.uploadFile(inputStream, ftpFileName.substring(indexId+1),
//						"./"+ftpFileName.substring(0, indexId+1));
				result = ftpUtil.uploadFile(inputStream, ftpFileName, server.getDir());
				if (!result)
					throw new Exception("uploadRemoPicFileToFtpServer is failure,name:"
							+ ftpFileName + " address:" + server.getAddress());
				ftpUtil.close();
			}
			
		}catch (Exception e) {
			Log.e(TAG, "***uploadRemoPicFileToFtpServer exception:"+e);
			throw e;
		}finally{
			//--关闭远端输入流
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	
	/**
	 * 将本地文件上传到远程ftp服务器(已做负载均衡处理)
	 * @param ftpFileName ftp上文件的名字
	 * @param inputStream 本地文件流
	 * @throws Exception
	 */
	public static void uploadFileToFtpServer(String ftpFileName, InputStream inputStream) throws Exception{
		Log.i(TAG, "***uploadFileToFtpServer begin");
		try{			
			//--将输入流传入ftp中
			FtpUtil ftpUtil = new FtpUtil();
			List<FtpServerBean> serverList = FtpUtil.getFtpServerList();
			for (FtpServerBean server : serverList) {
				boolean result = ftpUtil.ftpLogin(server);
				if (!result)
					throw new Exception(
							"loginning ftp server is failure,address:"
									+ server.getAddress() + " port:"
									+ server.getPort());
				//--将路径和文件分开
//				int indexId = ftpFileName.lastIndexOf("/");
//				
//				result = ftpUtil.uploadFile(inputStream, ftpFileName.substring(indexId+1),
//						"./"+ftpFileName.substring(0, indexId+1));
				result = ftpUtil.uploadFile(inputStream, ftpFileName, server.getDir());
				if (!result)
					throw new Exception("uploadFileToFtpServer is failure,name:"
							+ ftpFileName + " address:" + server.getAddress());
				ftpUtil.close();
			}
			
		}catch (Exception e) {
			Log.e(TAG, "***uploadFileToFtpServer exception:"+e);
			throw e;
		}finally{
			//--关闭远端输入流
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	

}
