package com.ipanel.web.kgbgsys.utils;

import java.util.Map;

import com.ipanel.webapp.framework.util.Log;
import com.sun.star.uno.RuntimeException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	private static JedisPool jedisPool;
	private static final String TAG = "RedisUtils";
	
	static {
	}

	/***********************
	 * read&set redis
	 * 
	 * @throws Exception
	 *******************************************/

	public static Jedis getJedisResource() throws Exception {
		try {
			// GET_JEDIS_COUNT++;
			Jedis jedis = jedisPool.getResource();
			return jedis;
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}
	}

	public static void returnJedisResource(Jedis jedis) {
		if(jedis!=null)
		jedisPool.returnResource(jedis); // returnResource(Jedis) ��API������3.0�汾������
//		if(jedis!=null)
//		jedis.close();
	}
	
	public static void setToken(String token, Map<String, String> redisMap){
		Jedis jedis = null;
		String str = null;
		int waitTime = 21600;
		try {
			waitTime = Integer.valueOf(str);
		} catch (Exception e) {
			waitTime = 21600;
		}
		try {
			Log.i(TAG, "��ʼ����token");
			jedis = getJedisResource();
			jedis.del(token);
			Log.i(TAG, "����token��Ϣ");
			jedis.hmset(token,redisMap);
			Log.i(TAG, "����token����ʱ��");
			jedis.expire(token, waitTime);
			Log.i(TAG, "����token�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "����tokenʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("����tokenʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}

	public static void setRecord(String key, String value){
		Jedis jedis = null;
		try {
			Log.i(TAG, "��ʼ����record");
			jedis = getJedisResource();
			jedis.del(key);
			Log.i(TAG, "����record��Ϣ");
			jedis.set(key, value);
			Log.i(TAG, "����record�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "����recordʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("����recordʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static void deleteToken(String token) {
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			jedis.del(token);
			Log.i(TAG, "ɾ��token�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ɾ��tokenʧ��");
			if (null != jedis) {
				 jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("ɾ��tokenʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static void deleteRecord(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			jedis.del(key);
			Log.i(TAG, "ɾ��record�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ɾ��recordʧ��");
			if (null != jedis) {
				 jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("ɾ��recordʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}

	public static Map<String, String> getToken(String token){
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			Map<String, String> remap = jedis.hgetAll(token);
			Log.i(TAG, "��ȡtoken�ɹ�");
			return remap;
		} catch (Exception e) {
			Log.e(TAG, "��ȡtokenʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("��ȡtokenʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static String getRecord(String key){
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			String value = jedis.get(key);
			Log.i(TAG, "��ȡrecord�ɹ�");
			return value;
		} catch (Exception e) {
			Log.e(TAG, "��ȡrecordʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("��ȡrecordʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static void expireToken(String token){
		Jedis jedis = null;
		String str = null;
		int waitTime = 21600;
		try {
			waitTime = Integer.valueOf(str);
		} catch (Exception e) {
			waitTime = 21600;
		}
		try {
			jedis = getJedisResource();
			jedis.expire(token, waitTime);
			Log.i(TAG, "�ӳ�token����ʱ��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "�ӳ�token����ʱ��ʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("�ӳ�token����ʱ��ʧ��");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static boolean checkToken(String token){
		Jedis jedis = null;
		boolean bl = false;
		try {
			jedis = getJedisResource();
			bl = jedis.exists(token);
			Log.i(TAG, "��֤token�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��֤tokenʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("��֤tokenʧ��");
		} finally {
			returnJedisResource(jedis);
		}
		return bl;
	}
	
	public static boolean checkRecord(String key){
		Jedis jedis = null;
		boolean bl = false;
		try {
			jedis = getJedisResource();
			bl = jedis.exists(key);
			Log.i(TAG, "��֤record�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��֤recordʧ��");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("��֤recordʧ��");
		} finally {
			returnJedisResource(jedis);
		}
		return bl;
	}
}
