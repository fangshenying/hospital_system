package com.ipanel.web.kgbgsys.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ipanel.webapp.framework.util.CommonUtil;
import com.ipanel.webapp.framework.util.Log;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FtpUtil {
	private static String TAG = "FtpUtil";
	private static final String TAG_HOST = "host";
	private static final String TAG_URL = "url";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_PORT = "port";
	private static final String TAG_DIR = "dir";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";
	private FTPClient ftpClient;
	private static List<FtpServerBean> ftpServerList;
	private String ftpRootPath = null;

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	static {
		ftpServerList = new ArrayList<FtpServerBean>();
		String ftpConfigFilePath = CommonUtil.getAbsPathOfProject() + "WEB-INF"
				+ File.separator + "classes" + File.separator
				+ "ftpService.xml";
		try {
			File file = new File(ftpConfigFilePath);
			FileInputStream fis = new FileInputStream(file);
			Document document = new SAXReader().read(fis);
			Element rootElement = document.getRootElement();

			@SuppressWarnings("unchecked")
			List<Element> hostList = rootElement.elements(TAG_HOST);
			for (Element host : hostList) {
				String url = host.element(TAG_URL).getText();
				String address = host.element(TAG_ADDRESS).getText();
				String port = host.element(TAG_PORT).getText();
				String dir = host.element(TAG_DIR).getText();
				String userName = host.element(TAG_USERNAME).getText();
				String password = host.element(TAG_PASSWORD).getText();
				FtpServerBean bean = new FtpServerBean();
				bean.setAddress(address);
				bean.setDir(dir);
				bean.setPassword(password);
				bean.setPort(Integer.parseInt(port));
				bean.setUrl(url);
				bean.setUserName(userName);
				ftpServerList.add(bean);
			}
		} catch (Exception e) {
			Log.e(TAG, "****loadFtpServerInfo throw exception:" + e);
			System.exit(-1);
		}
	}

	public static List<FtpServerBean> getFtpServerList() {
		return ftpServerList;
	}

	public static String getUrlPrex() {
		if (ftpServerList != null && ftpServerList.size() > 0)
			return ftpServerList.get(0).getUrl();
		else
			return "";
	}

	public boolean ftpLogin(FtpServerBean ftpBean) {
		ftpClient = new FTPClient();
		boolean isLogin = false;
		// FTPClientConfig ftpClientConfig = new FTPClientConfig(
		// FTPClientConfig.SYST_NT);
		// ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		this.ftpClient.setControlEncoding("UTF-8");
		// this.ftpClient.configure(ftpClientConfig);
		try {
			if (ftpBean.getPort() > 0) {
				this.ftpClient.connect(ftpBean.getAddress(), ftpBean.getPort());
			} else {
				this.ftpClient.connect(ftpBean.getAddress());
			}
			this.ftpClient.setDataTimeout(18000);
			ftpClient.enterLocalPassiveMode();
			// FTP服务器连接回答
			int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				return isLogin;
			}
			this.ftpClient.login(ftpBean.getUserName(), ftpBean.getPassword());
			// this.ftpClient.setFileType(FTPClient.FILE_STRUCTURE);
			isLogin = true;
			ftpRootPath = ftpClient.printWorkingDirectory();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ftpClient.setBufferSize(1024 * 2);
		this.ftpClient.setDataTimeout(2000);
		return isLogin;
	}

	/**
	 * 退出并关闭FTP连接
	 * 
	 */
	public void close() {
		if (null != this.ftpClient && this.ftpClient.isConnected()) {
			try {
				boolean reuslt = this.ftpClient.logout();// 退出FTP服务器
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					this.ftpClient.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param localFilePath
	 *            本地文件名及路径
	 * @param remoteFileName
	 *            远程文件名称
	 * @return
	 */
	public boolean downloadFile(String localFilePath, String remoteFileName) {
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			outStream = new BufferedOutputStream(new FileOutputStream(
					localFilePath));
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * 下载文件
	 * 
	 * @param localFilePath
	 *            本地文件
	 * @param remoteFileName
	 *            远程文件名称
	 * @return
	 */
	public boolean downloadFile(File localFile, String remoteFileName) {
		BufferedOutputStream outStream = null;
		FileOutputStream outStr = null;
		boolean success = false;
		try {
			outStr = new FileOutputStream(localFile);
			outStream = new BufferedOutputStream(outStr);
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outStream) {
					try {
						outStream.flush();
						outStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != outStr) {
					try {
						outStr.flush();
						outStr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
		return success;
	}

	/**
	 * 上传文件
	 * 
	 * @param localFilePath
	 *            本地文件路径及名称
	 * @param remoteFileName
	 *            FTP 服务器文件名称
	 * @return
	 */
	public boolean uploadFile(InputStream inStream, String remoteFileName,
			String remoteDir) {
		boolean success = false;
		BufferedInputStream binStream = null;
		try {
			Log.i(TAG, "***enter uploadFile remoteDir:" + remoteDir);
			if (remoteDir != null && !remoteDir.equals("./")
					&& !remoteDir.equals("/") && remoteDir.contains("/")) {
				// System.out.println("****remoteFileName:" + remoteFileName);
				// 创建服务器远程目录结构，创建失败直接返回
				if (!CreateDirecroty(remoteDir)) {
					Log.i(TAG, "****CreateDirecroty is failure!!!,remoteDir:"
							+ remoteDir);
					return false;
				}
			}
			// boolean changeResult =
			// this.ftpClient.changeWorkingDirectory(remoteDir);
			// if (!changeResult) {
			// Log.i(TAG, "**changeWorkingDirectory is failure,remoteDir:" +
			// remoteDir);
			// return changeResult;
			// }
			binStream = new BufferedInputStream(inStream);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			success = this.ftpClient.storeFile(remoteFileName, binStream);
			Log.i(TAG, "***storeFile result is " + success);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (binStream != null) {
				try {
					binStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * 上传文件
	 * 
	 * @param localFilePath
	 *            本地文件路径及名称
	 * @param remoteFileName
	 *            FTP 服务器文件名称
	 * @return
	 */
	public boolean uploadFile(String localFilePath, String remoteFileName,
			String remoteDir) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			Log.i(TAG,
					"***enter uploadFile localFilePath:" + localFilePath
							+ " remoteFileName:" + remoteFileName
							+ " remoteDir:" + remoteDir + " workingDir:"
							+ ftpClient.printWorkingDirectory());
			// this.ftpClient.changeWorkingDirectory("/");
			if (remoteDir != null && !remoteDir.equals("./")
					&& !remoteDir.equals("/") && remoteDir.contains("/")) {
				// System.out.println("****remoteFileName:" + remoteFileName);
				// 创建服务器远程目录结构，创建失败直接返回
				if (!CreateDirecroty(remoteDir)) {
					Log.w(TAG, "***CreateDirecroty is failure remoteDir:"
							+ remoteDir);
					return false;
				}
			}
			String workingDir = this.ftpClient.printWorkingDirectory();
			Log.i(TAG, "***workingdir:" + workingDir);
			// boolean changeResult = this.ftpClient
			// .changeWorkingDirectory(remoteDir);
			// Log.i(TAG, "***********uploadFile changeResult:" + changeResult
			// + " remoteDir:" + remoteDir);
			// Log.i(TAG, "***********uploadFile changeResult:" + changeResult
			// + " remoteDir:" + remoteDir + " localFilePath:"
			// + localFilePath);
			// if (!changeResult)
			// return changeResult;

			inStream = new BufferedInputStream(new FileInputStream(
					localFilePath));
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			success = ftpClient.storeFile(remoteFileName, inStream);
			Log.i(TAG, "***success:" + success);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * 上传文件
	 * 
	 * @param localFilePath
	 *            本地文件
	 * @param remoteFileName
	 *            FTP 服务器文件名称
	 * @return
	 */
	public boolean uploadFile(File localFile, String remoteFileName) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			success = this.ftpClient.storeFile(remoteFileName, inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/** */
	/**
	 * 递归创建远程服务器目录(支持绝对路径和相对路径)
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * 
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public boolean CreateDirecroty(String remote) {
		try {
			boolean success = true;
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			// 如果远程目录不存在，则递归创建远程服务器目录
			if (!directory.equalsIgnoreCase("/")
					&& !ftpClient.changeWorkingDirectory(new String(directory))) {
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(remote.substring(start,
							end));
					Log.i(TAG,
							"***workingDir:"
									+ ftpClient.printWorkingDirectory()
									+ " subDirectory:" + subDirectory);
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							System.out.println("创建目录失败");
							success = false;
							return success;
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return success;
		} catch (Exception e) {
			Log.e(TAG, "**CreateDirecroty throw e:" + e);
			return false;
		}
	}

	/**
	 * 将local目录下的子目录及文件上传到ftp服务器上
	 * 
	 * @param local
	 * @param relativePath
	 * @param remote
	 * @return
	 */
	public boolean uploadDir(String local, String relativePath, String remote) {
		try {
			Log.i(TAG, "***enter uploadDir local:" + local + " relativePath:"
					+ relativePath + " remote:" + remote + " workingDir:"
					+ ftpClient.printWorkingDirectory());
			if (remote.equals("./"))
				remote = "/";
			boolean success = false;
			String localpath = local;
			if (local.endsWith(File.separator))
				localpath = local.substring(0, local.length() - 1);
			File localdir = new File(local);
			// 要上传的是否存在
			if (!localdir.exists()) {
				return success;
			}
			// 要上传的是否是文件夹
			if (!localdir.isDirectory()) {
				return success;
			}
			String[] subfiles = localdir.list();
			for (String subfile : subfiles) {
				File file = new File(localpath, subfile);
				Log.i(TAG,
						"***abs filePath:" + file.getAbsolutePath().toString()
								+ " is dir:" + file.isDirectory());
				if (file.exists()) {
					if (file.isDirectory()) {
						boolean result = uploadDir(file.getAbsoluteFile()
								.toString(), relativePath + subfile + "/",
								remote);
						if (!result)
							return result;
					} else {
						String remotePath = remote + relativePath;
						boolean result = uploadFile(file.getAbsolutePath(),
								subfile, remotePath);
						Log.i(TAG, "**remotePath:" + remotePath + " result:"
								+ result);
						if (!result) {
							Log.i(TAG, "***start to return result:" + result);
							return result;
						}
						Log.i(TAG, "****hehe");
						ftpClient.changeWorkingDirectory(ftpRootPath);

					}
				}
			}
			Log.i(TAG, "----------------return true");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "***uploadDir throw excepiton:" + e);
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean delFile(String fileName) {
		try {
			return ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			Log.e(TAG, "***delFile fileName:" + fileName + " throw exception:"
					+ e);
			return false;
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param ftpPath
	 * @return
	 */
	public boolean deleteDir(String ftpPath) {
		boolean flag = false;
		try {
			flag = iterateDelete(ftpPath);
		} catch (IOException e) {
			// TODO 异常处理块
			Log.e(TAG, "***deleteDir throw exception:" + e);
		}
		return flag;
	}

	private boolean iterateDelete(String ftpPath) throws IOException {
		Log.i(TAG, "--------------enter iterateDelete ftpPath:" + ftpPath
				+ " working:" + ftpClient.printWorkingDirectory());
		FTPFile[] files = ftpClient.listFiles(ftpPath);
		Log.i(TAG, "--------------files.length:" + files.length);
		boolean flag = false;
		for (FTPFile f : files) {
			String path = ftpPath + "/" + f.getName();
			Log.i(TAG,
					"-------------isFile:" + f.isFile() + " isDir:"
							+ f.isDirectory() + " path:" + f.getName());
			if (f.isFile()) {
				// 是文件就删除文件
				ftpClient.deleteFile(path);
			} else if (f.isDirectory()) {
				iterateDelete(path);
			}
		}
		// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
		FTPFile[] files2 = ftpClient.listFiles(ftpPath);
		if (files2.length == 0) {
			flag = ftpClient.removeDirectory(ftpPath);
		} else {
			flag = false;
		}
		return flag;
	}

	// 判断是否是目录
	public boolean isDir(String fileName) {
		try {
			// 切换目录，若当前是目录则返回true,否则返回true。
			return ftpClient.changeWorkingDirectory(fileName);
		} catch (Exception e) {
			Log.e(TAG, "****isDir throw exception:" + e);
		}
		return false;
	}

	/**
	 * 下载目录
	 * 
	 * @param dir
	 * @param downloadPath
	 * @return
	 * @throws Exception
	 */
	public boolean downDir(String dir, String downloadPath) {
		try {
			System.out.println("------------------------enter iterateDown dir:"
					+ dir + " downloadPath:" + downloadPath);
			// 列出这个地址对应到的是文件夹还是文件
			FTPFile[] files = ftpClient.listFiles(dir);
			System.out.println("***files.size:" + files.length);
			for (FTPFile f : files) {
				// 如果当前目录还没有创建，那么就在这里创建
				File filedown = new File(downloadPath);
				if (!filedown.exists()) {
					filedown.mkdirs();
				}
				String localPath = downloadPath + File.separator + f.getName();
				System.out.println("***localPath:" + localPath + " ftp file:"
						+ f.getName() + " isFile:" + f.isFile() + " isDir:"
						+ f.isDirectory());
				File file = new File(localPath);
				if (f.isFile()) {
					FileOutputStream fos = new FileOutputStream(localPath);
					String path = dir + "/" + f.getName();
					boolean result = ftpClient.retrieveFile(path, fos);
					System.out.println("***path:" + path + " localPath:"
							+ localPath + " result:" + result);
					IOUtils.closeQuietly(fos);
				} else if (f.isDirectory()) {
					file.mkdirs();
					downDir(dir + "/" + f.getName(), localPath);
				}
			}
			return true;
		} catch (Exception e) {
			Log.e(TAG, "****downDir throw exception:" + e);
			return false;
		}

	}
	
	
	/**
	 * 检查ftp服务器上某个目录或者文件是否存在
	 * 
	 * @param path, pathFlag(0:文件，1:目录)
	 * @return 存在 true，不存在 false.
	 * @throws IOException
	 */
	public boolean existDirectoryOrFile(String path, int pathFlag) throws IOException {
		
		boolean flag = false;
		path = path.replace('\\', '/');
		FTPFile[] ftpFileArr = ftpClient.listFiles();	
		//--System.out.println("file_name:"+ftpFile.getName());
		//--判断是文件还是文件夹
		if(pathFlag == 1){
			//--文件夹
			for (FTPFile ftpFile : ftpFileArr) {
				if (ftpFile.isDirectory()
						&& ftpFile.getName().equalsIgnoreCase(path)) {
					flag = true;
					break;
				}
			}
		} else {
			//--文件
			for (FTPFile ftpFile : ftpFileArr) {
				//--System.out.println("ftpFile.getName():"+ftpFile.getName());
				if (ftpFile.isFile()
						&& ftpFile.getName().equalsIgnoreCase(path)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	

	public static class FtpServerBean {
		private String dir;
		private String userName;
		private String password;
		private String address;
		private String url;
		private int port;

		public FtpServerBean() {
		}

		public FtpServerBean(String dir, String userName, String password,
				String address, String url, int port) {
			this.dir = dir;
			this.userName = userName;
			this.password = password;
			this.address = address;
			this.url = url;
			this.port = port;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}

	public static void main(String arg[]) throws IOException {
		 FtpServerBean bean = new FtpServerBean();
		 bean.setAddress("192.168.101.150");
		 bean.setPort(21);
		 bean.setUserName("fangsy");
		 bean.setPassword("123456");
		 bean.setDir("/");
		 FtpUtil ftpUtil = new FtpUtil();
		 boolean ftpLogin = ftpUtil.ftpLogin(bean);
		 System.out.println("ftpLogin:"+ftpLogin);
		
		
		 //--测试删除文件,结果OK(注:删除文件和目录是文件不存在并不会抛异常，但是返回的标志位还是false的)
		 // boolean delFileResult = ftpUtil.delFile("player.apk");
		 // System.out.println("delFileResult:" + delFileResult);
		
		 //--测试删除目录,结果OK
		 // boolean delDirResult = ftpUtil.deleteDir("./shopping_base/");
		 // System.out.println("delDirResult:" + delDirResult);
		
		 //--测试下载目录,结果OK
		 // boolean downResult = ftpUtil.downDir("./Data/signData/",
		 // "D:/usr/local/");
		 // System.out.println("downResult:" + downResult);
		 
		 //--检查ftp服务器上某个目录或者文件是否存在,结果OK
		 //System.out.println("文件或目录存在与否："+ftpUtil.existDirectoryOrFile("123.jpg",0));
		 
		 //--测试递归创建文件夹
		 ftpUtil.CreateDirecroty("./ceshi/fangsy/wenjin/");
		 ftpUtil.close();
		 
	}
	
}
