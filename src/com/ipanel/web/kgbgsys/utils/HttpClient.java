package com.ipanel.web.kgbgsys.utils;

/**
 * IPMWork HttpClient.java
 * 2013-5-31
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ipanel.webapp.framework.util.Log;

public final class HttpClient {

	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_DELETE = "DELETE";
	public static final Integer CONNECT_TIME_OUT = 10000;
	public static int totalNum = 0;
	
	private boolean needProxy;
	
	private String proxyHost;
	
	private int proxyPort;

	public static HttpClient create(URL url) {
		return new HttpClient(url);
	}
	
	public static HttpClient createPorxyClient(URL url ,String proxyHost ,int proxyPort) {
		HttpClient client = new HttpClient(url);
		client.setNeedProxy(true);
		client.setProxyHost(proxyHost);
		client.setProxyPort(proxyPort);
		return client;
	}

	public boolean getNeedProxy() {
		return needProxy;
	}

	public void setNeedProxy(boolean needProxy) {
		this.needProxy = needProxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}


	URL mURL;
	String mMethod = HTTP_METHOD_GET;
	String mData = "";
	Map<String, String> mPropertys;

	HttpClient(URL url) {
		mURL = url;
		mPropertys = new HashMap<String, String>();
	}

	public void setRequestMethod(String method) {
		if (method == null || "".equals(method))
			return;
		if (HTTP_METHOD_POST == method || HTTP_METHOD_GET == method || HTTP_METHOD_PUT == method
				|| HTTP_METHOD_DELETE == method) {
			mMethod = method;
		}
	}

	public void setRequestData(String data) {
		if (data == null)
			return;
		mData = data;
	}

	public void setRequestProperty(String name, String value) {
		if (name == null || "".equals(name) || value == null || "".equals(value))
			return;
		mPropertys.put(name, value);
	}

	public String execute() throws IOException {
		return execute("UTF-8", CONNECT_TIME_OUT);
	}

	public String execute(String encode) throws IOException {
		return execute(encode, CONNECT_TIME_OUT);
	}

	@SuppressWarnings("resource")
	public String execute(String encode, Integer timeout, File dataFile) throws IOException {
		HttpURLConnection conn = null;
		if(needProxy){
			InetSocketAddress address = new InetSocketAddress(proxyHost, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			conn = (HttpURLConnection) mURL.openConnection(proxy);
		}else{
			conn = (HttpURLConnection) mURL.openConnection();
		}
		conn.setRequestMethod(HTTP_METHOD_POST);
		conn.setDoInput(true);
		conn.setConnectTimeout(timeout);

		Set<String> mKeys = mPropertys.keySet();
		for (String key : mKeys) {
			conn.setRequestProperty(key, mPropertys.get(key));
		}

		conn.setRequestProperty("Content-type", "");
		conn.setDoOutput(true);
		DataInputStream postIn = new DataInputStream(new FileInputStream(dataFile));
		// byte[] test = new byte[postIn.available()];
		// postIn.read(test);

		// String str = new String(test,"gb2312");
		// System.out.println("***" + str);
		// out.write(str.getBytes());
		OutputStream out = conn.getOutputStream();
		int bytes = 0;
		byte[] buffer = new byte[1024 * 20];
		while ((bytes = postIn.read(buffer)) != -1) {
			// Log.i("httpClient", "send msg length:" + bytes);
			out.write(buffer, 0, bytes);
			out.flush();
		}
		// postIn.close();
		// conn.setRequestProperty("Content-Length",
		// String.valueOf(fileLength));
		// out.write(test);
		out.close();
		int code = conn.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), encode);
			BufferedReader in = new BufferedReader(isr);
			StringBuffer sbuf = new StringBuffer();
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				sbuf.append(inputLine);
			}
			return sbuf.toString();
		}
		return "";
	}

	public String execute(String encode, Integer timeout) throws IOException {
		HttpURLConnection conn = null;
		if(needProxy){
			InetSocketAddress address = new InetSocketAddress(proxyHost, proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			conn = (HttpURLConnection) mURL.openConnection(proxy);
		}else{
			conn = (HttpURLConnection) mURL.openConnection();
		}
		conn.setRequestMethod(mMethod);
		conn.setDoInput(true);
		conn.setConnectTimeout(timeout);

		Set<String> mKeys = mPropertys.keySet();
		for (String key : mKeys) {
			conn.setRequestProperty(key, mPropertys.get(key));
		}

		if (HTTP_METHOD_POST == mMethod) {
			conn.setRequestProperty("Content-type", "");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", String.valueOf(mData.getBytes().length));
			conn.getOutputStream().write(mData.getBytes(encode));
		}
		int code = conn.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), encode);
			BufferedReader in = new BufferedReader(isr);
			StringBuffer sbuf = new StringBuffer();
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				sbuf.append(inputLine);
			}
			return sbuf.toString();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static void testUrl(String url) {
		try {
			// HttpClient http = new HttpClient(new
			// URL("http://192.168.21.254:8081/calibration/m/login"));
			HttpClient http = new HttpClient(new URL(url));
			http.setRequestMethod(HttpClient.HTTP_METHOD_POST);
			http.setRequestProperty("uuid", "707fb7b4ee184967bec3ca6df31dcae1");
			// http.setRequestProperty("fileName","20160707174520.zip");
			http.setRequestProperty("lasttime", "20160727121210");
			http.setRequestProperty("fileName", "20160727121210.txt");
			http.setRequestProperty("chnlId", "6");
			http.setRequestProperty("day", "20160727");
			long curTime = System.currentTimeMillis() / 1000;
			System.out.println(curTime);
			http.setRequestProperty("timestamp", String.valueOf(curTime));
			String orig = "707fb7b4ee184967bec3ca6df31dcae1" + curTime + "9f7eafb4f95a4bf4";
			http.setRequestProperty("signature", EncoderHandler.encode(EncoderHandler.ALGORITHM_MD5, orig));
			File dataFile = new File("d:/chnllist.txt");
			String respCode = new String(http.execute("UTF-8", 100000, dataFile));
			// http.setRequestMethod(HTTP_METHOD_GET);
			// String respCode = new String(http.execute());
			System.out.println("****finished,respCode:" + respCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpClient sendRequest(String url) {
		try {
			HttpClient http = new HttpClient(new URL(url));
			http.setRequestMethod(HttpClient.HTTP_METHOD_POST);
			return http;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static class TestUrlThread implements Runnable {
		String url;
		String threadName;
		long index = 0;

		public TestUrlThread(String url, String treadName) {
			this.url = url;
			this.threadName = treadName;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			//File dataFile = new File("/Users/bllllue/Desktop/com20_stbuser3000_3.xlsx.zip");
			long startTime = System.currentTimeMillis();
			while (true) {
				String respCode = null;
				try {
					index++;
					// respCode = new
					// String(http.execute("UTF-8",100000,dataFile));
					long seTime = System.currentTimeMillis();
					HttpClient http = sendRequest(url);
					long curTime = System.currentTimeMillis() / 1000;
					http.setRequestProperty("id", "12");
					http.setRequestProperty("timestamp", String.valueOf(curTime));
					String orig = "8fa4c58fe00d6a74" + curTime + "3";
					http.setRequestProperty("signature", EncoderHandler.encode(EncoderHandler.ALGORITHM_MD5, orig));
//					respCode = new String(http.execute("UTF-8", 2000, dataFile));
					respCode = new String(http.execute("UTF-8", 20000));
					System.out.println("****finished,respCode:" + respCode);
					Log.i("Test", "tread:" + threadName + ",num:" + index + ", finished use time:"
							+ (System.currentTimeMillis() - seTime) + ",respCode:" + respCode);
					if (index > 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Log.i("TEST", "tread:" + threadName + ",request end use time:" + (System.currentTimeMillis() - startTime));
		}

	}

	public static void main(String[] args) {

		/*for (int i = 0; i < 20; i++) {
			Thread t2 = new Thread(new TestUrlThread("http://114.55.130.88:8081/m/src/pic/download", "thread" + i));
			t2.start();
			Log.i("TEST", "thread num : " + i + "start!");
		}*/

		/*
		 * try { FileFiend.unZipFile("D:/apacheWeb/epg/test/20160902145030.zip",
		 * "D:/apacheWeb/epg/test/pic"); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		// Thread t1 = new Thread(new
		// TestUrlThread("http://192.168.21.184:8081/calibration/m/src/epg/updata"));
		// t1.start();

		Thread t2 = new Thread(new TestUrlThread("http://192.168.20.250:9080/kindergarten_backOffice/interface/query/camera", "testTh"));
		// Thread t2 = new Thread(new TestUrlThread(
		// "http://192.168.20.121:8081/m/dst/ret/download", "testTh"));
		// Thread t2 = new Thread(new
		// TestUrlThread("http://192.168.20.250:8081/m/dst/pic/download","testTh"));
		t2.start();

	}
}