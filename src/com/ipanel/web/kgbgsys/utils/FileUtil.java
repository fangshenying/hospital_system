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
	 * �ϴ�Զ��ͼƬ��ָ����ftp�������ϣ��������ؾ��⴦��
	 * @param ftpFileName ftp���ļ�������
	 * @param remotePicUrl Զ��ͼƬ��url��ַ
	 * @throws Exception
	 */
	public static void uploadRemoPicFileToFtpServer(String ftpFileName, String remotePicUrl) throws Exception{
		Log.i(TAG, "***uploadRemoPicFileToFtpServer begin");
		InputStream inputStream = null;
		try{
			//--��ȡԶ��ͼƬ������������
			URL url = new URL(remotePicUrl);
			//--URLConnection con = url.openConnection();
			//--
			InetSocketAddress address = new InetSocketAddress(Conf.PROXY_IP, Integer.parseInt(Conf.PROXY_PORT));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			URLConnection con = url.openConnection(proxy);
			//--
			con.setConnectTimeout(5000);
			inputStream = con.getInputStream();
			
			//--������������ftp��
			FtpUtil ftpUtil = new FtpUtil();
			List<FtpServerBean> serverList = FtpUtil.getFtpServerList();
			for (FtpServerBean server : serverList) {
				boolean result = ftpUtil.ftpLogin(server);
				if (!result)
					throw new Exception(
							"loginning ftp server is failure,address:"
									+ server.getAddress() + " port:"
									+ server.getPort());
				//--��·�����ļ��ֿ�
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
			//--�ر�Զ��������
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	
	/**
	 * �������ļ��ϴ���Զ��ftp������(�������ؾ��⴦��)
	 * @param ftpFileName ftp���ļ�������
	 * @param inputStream �����ļ���
	 * @throws Exception
	 */
	public static void uploadFileToFtpServer(String ftpFileName, InputStream inputStream) throws Exception{
		Log.i(TAG, "***uploadFileToFtpServer begin");
		try{			
			//--������������ftp��
			FtpUtil ftpUtil = new FtpUtil();
			List<FtpServerBean> serverList = FtpUtil.getFtpServerList();
			for (FtpServerBean server : serverList) {
				boolean result = ftpUtil.ftpLogin(server);
				if (!result)
					throw new Exception(
							"loginning ftp server is failure,address:"
									+ server.getAddress() + " port:"
									+ server.getPort());
				//--��·�����ļ��ֿ�
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
			//--�ر�Զ��������
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	

}
