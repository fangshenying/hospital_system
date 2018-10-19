package com.ipanel.web.kgbgsys.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipanel.webapp.framework.util.Encrypt;
import com.sun.star.uno.RuntimeException;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;


/**
 * �ַ��๫�����߰�
 * 
 * 
 */
public class StringUtils {
	
	private static Random r = new Random();
	
	public static CodepageDetectorProxy detector = null;
	
	private static final String prefix = "";
	
	static{
		detector = CodepageDetectorProxy.getInstance();
		//�����ϣ���ж�xml��encoding������Ҫ�жϸ�xml�ļ��ı��룬�����ע�͵�  
		detector.add(new ParsingDetector(false));
		//�ַ������
		detector.add(JChardetFacade.getInstance());  
		// ASCIIDetector����ASCII����ⶨ  
		detector.add(ASCIIDetector.getInstance());  
		// UnicodeDetector����Unicode�������Ĳⶨ  
		detector.add(UnicodeDetector.getInstance());
	}

	private static char src[] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	/**
	 * �ж��ַ����ı���
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * Check if a String has length.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.trim().length() > 0);
	}

	/**
	 * �ж��ַ����Ƿ�������
	 * 
	 * @param s
	 * @return �����֣�true;�������֣�false
	 */
	public static boolean isNumeric(String str) {
		boolean flag = true;
		String[] strs = str.split("\\.");
		if (strs.length > 2) {
			return false;
		}
		for (String s : strs) {
			if (s != null && !s.equals("")) {
				char[] numbers = s.toCharArray();
				for (int i = 0; i < numbers.length; i++) {
					if (!Character.isDigit(numbers[i])) {
						flag = false;
						break;
					}
				}
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * ISOת����GB2312
	 * 
	 * @param str
	 * @return
	 */
	public static String isoToOther(String str, String encoding) {
		try {
			if (str != null && !str.equals("")) {
				byte[] byteStr = str.getBytes("ISO-8859-1");
				return new String(byteStr, encoding);
			} else {
				return "";
			}
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * StringתDouble
	 * 
	 * @param s
	 * @return
	 */
	public static Double strToDouble(String s) {
		Double temp = -1D;
		if (hasLength(s)) {
			if (isNumeric(s)) {
				temp = Double.parseDouble(s);
			}
		}
		return temp;
	}

	public static long strToLong(String s) {
		long temp = -1;
		if (hasLength(s)) {
			if (isNumeric(s)) {
				temp = Long.parseLong(s);
			}
		}
		return temp;
	}

	/**
	 * ���ַ���ת��������
	 * 
	 * @param s
	 *            ��ת�����ַ���
	 * @return int�������ַ����������֣��򷵻�null
	 * @throws DTOException
	 */
	public static int strToInt(String s) throws Exception {
		int temp = 0;
		if (hasLength(s)) {
			if (isNumeric(s.trim())) {
				try {
					temp = Integer.parseInt(s);
				} catch (Exception e) {
					throw new RuntimeException("����Ĳ�������");
				}
			}
		}
		return temp;
	}

	/**
	 * ���ַ���ת����date
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String s, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(s);
	}

	/**
	 * ��������ʽ�ж��ַ����Ƿ���urlencode����
	 * 
	 * @param s
	 *            String
	 * @return boolean
	 */
	public static boolean isUrlEncode(String s) {
		boolean tag = false;
		if (s == null || s.length() < 3) {
			return false;
		}
		if (s.length() > 3) {
			s = s.substring(0, 3);
		}
		String regex = "%[A-Z0-9]{2}";
		if (s != null && !"".equals(s)) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(s);
			tag = m.matches();
		}
		return tag;
	}

	public static String parseHTML(String cnt) {
		return cnt.replace("<", "&lt;").replace(">", "&gt;")
				.replace("\"", "&quot;").replace("'", "&#39;");
	}

	public static String[] parseHTML(String[] cnts) {
		String[] strs = new String[cnts.length];
		int c = 0;
		for (String str : cnts) {
			strs[c++] = str.replace("<", "&lt;").replace(">", "&gt;")
					.replace("\"", "&quot;").replace("'", "&#39;");
		}
		return strs;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/** * ��ȡ�ַ����ĳ��ȣ���������ģ���ÿ�������ַ���Ϊ2λ * @param value ָ�����ַ��� * @return �ַ����ĳ��� */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]"; /* ��ȡ�ֶ�ֵ�ĳ��ȣ�����������ַ�����ÿ�������ַ�����Ϊ2������Ϊ1 */
		for (int i = 0; i < value.length(); i++) { /* ��ȡһ���ַ� */
			String temp = value.substring(i, i + 1); /* �ж��Ƿ�Ϊ�����ַ� */
			if (temp.matches(chinese)) { /* �����ַ�����Ϊ2 */
				valueLength += 2;
			} else { /* �����ַ�����Ϊ1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	public static String getUniqueId(int len) {
		return randomString(len);
	}
	private static String randomString(int length) {
		String str = "";
		int i = 0;
		while (i < length) {
			int c = r.nextInt(src.length);
			str = str + src[c];
			i++;
		}
		return str;
	}
	
	public static boolean checkImgType(String fileExt){
		if("png".equalsIgnoreCase(fileExt)){
			return true;
		}
		if("jpg".equalsIgnoreCase(fileExt)){
			return true;
		}
		if("gif".equalsIgnoreCase(fileExt)){
			return true;
		}
		if("jpeg".equalsIgnoreCase(fileExt)){
			return true;
		}
		return false;
	}
	
	/**
     * �ж��ַ��Ƿ�������
     *
     * @param c �ַ�
     * @return �Ƿ�������
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
 
    /**
     * �ж��ַ����Ƿ�������
     *
     * @param strName �ַ���
     * @return �Ƿ�������
     */
    public static boolean isMessyCode(String strName) {
    	Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");  
        Matcher m = p.matcher(strName);  
        String after = m.replaceAll("");  
        String temp = after.replaceAll("\\p{P}", "");  
        char[] ch = temp.trim().toCharArray();  
        float chLength = 0 ;  
        float count = 0;  
        for (int i = 0; i < ch.length; i++) {  
            char c = ch[i];  
            if (!Character.isLetterOrDigit(c)) {  
                if (!isChinese(c)) {  
                    count = count + 1;  
                }  
                chLength++;   
            }  
        }  
        float result = count / chLength ;  
        if (result > 0.4) {  
            return true;  
        } else {  
            return false;  
        }  
 
    }
	
    //����ͼƬ·�������Ӻ��ʵ�ǰ׺
  	public static String addPrefix(String pictureString){
  		if (null != pictureString) {
  			String[] pictures = pictureString.split(";");
  			pictureString = "";
  			for (int i = 0; i < pictures.length; i++) {
  				if (pictures[i].lastIndexOf('/') < pictures[i].length() - 1){
  					pictures[i] = pictures[i].substring(pictures[i].lastIndexOf('/')+1);
  				}
  				if (pictures[i].split("\\.").length > 1) {
  					pictureString += prefix + pictures[i] + ";";
  				}
  			}
  		}
  		if("".equals(pictureString)) {
  			pictureString = null;
  		}
  		return pictureString;
  	}
  	
  	//����ͼƬ·����ֻ����ͼƬ��
  	public static String removePrefix(String pictureString){
  		if (null != pictureString) {
  			String[] pictures = pictureString.split(";");
  			pictureString = "";
  			for (int i = 0; i < pictures.length; i++) {
  				if (pictures[i].lastIndexOf('/') < pictures[i].length() - 1){
  					pictures[i] = pictures[i].substring(pictures[i].lastIndexOf('/')+1);
  				}
  				if (pictures[i].split("\\.").length > 1) {
  					pictureString += pictures[i] + ";";
  				}
  			}
  		}
  		if("".equals(pictureString)) {
  			pictureString = null;
  		}
  		return pictureString;
  	}
  	
    //����ͼƬ·�������Ӻ��ʵ�ǰ׺(�ָ���Ϊ����)
  	public static String addPrefixForComma(String pictureString){
  		if (null != pictureString) {
  			String[] pictures = pictureString.split(",");
  			pictureString = "";
  			for (int i = 0; i < pictures.length; i++) {
  				if (pictures[i].lastIndexOf('/') < pictures[i].length() - 1){
  					pictures[i] = pictures[i].substring(pictures[i].lastIndexOf('/')+1);
  				}
  				if (pictures[i].split("\\.").length > 1) {
  					pictureString += prefix + pictures[i] + ",";
  				}
  			}
  			//--ȥ���ַ������Ķ���
  	  		if(pictureString.endsWith(",")){
  	  			pictureString = pictureString.substring(0, pictureString.length()-1);
  	  		}
  		}
  		if("".equals(pictureString)) {
  			pictureString = null;
  		}
  		return pictureString;
  	}
  	
  	//����ͼƬ·����ֻ����ͼƬ��(�ָ���Ϊ����)
  	public static String removePrefixForComma(String pictureString){
  		if (null != pictureString) {
  			String[] pictures = pictureString.split(",");
  			pictureString = "";
  			for (int i = 0; i < pictures.length; i++) {
  				if (pictures[i].lastIndexOf('/') < pictures[i].length() - 1){
  					pictures[i] = pictures[i].substring(pictures[i].lastIndexOf('/')+1);
  				}
  				if (pictures[i].split("\\.").length > 1) {
  					pictureString += pictures[i] + ",";
  				}
  			}
  	  		if(pictureString.endsWith(",")){
  	  			pictureString = pictureString.substring(0, pictureString.length()-1);
  	  		}
  		}
  		if("".equals(pictureString)) {
  			pictureString = null;
  		}
  		return pictureString;
  	}
    
	public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
		String str = "A_1_2";
		System.out.println(str.substring(2));
		System.out.println(Encrypt.e("111111"));
//		new StringUtils().createJs();
//		FileFiend.copyDirectory("/Users/bllllue/Applications/apache-tomcat-7.0.73/webapps/up_portal/1/portal/jiaoyu",
//				"/Users/bllllue/Applications/apache-tomcat-7.0.73/webapps/up_portal/2/portal/preview/jiaoyu");
//		FileFiend.copyDirectory("/Users/bllllue/Applications/apache-tomcat-7.0.73/webapps/up_portal/4/portal/jiaoyu",
//				"/Users/bllllue/Applications/apache-tomcat-7.0.73/webapps/up_portal/2/portal/preview/jiaoyu");
		long curT = System.currentTimeMillis() / 1000;
		System.out.println(curT);
		System.out.println(Encrypt.e("123456"));
		
	}
	
}
