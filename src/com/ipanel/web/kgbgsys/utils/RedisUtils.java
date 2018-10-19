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
		jedisPool.returnResource(jedis); // returnResource(Jedis) 此API将会在3.0版本被废弃
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
			Log.i(TAG, "开始设置token");
			jedis = getJedisResource();
			jedis.del(token);
			Log.i(TAG, "设置token信息");
			jedis.hmset(token,redisMap);
			Log.i(TAG, "设置token过期时间");
			jedis.expire(token, waitTime);
			Log.i(TAG, "设置token成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "设置token失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("设置token失败");
		} finally {
			returnJedisResource(jedis);
		}
	}

	public static void setRecord(String key, String value){
		Jedis jedis = null;
		try {
			Log.i(TAG, "开始设置record");
			jedis = getJedisResource();
			jedis.del(key);
			Log.i(TAG, "设置record信息");
			jedis.set(key, value);
			Log.i(TAG, "设置record成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "设置record失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("设置record失败");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static void deleteToken(String token) {
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			jedis.del(token);
			Log.i(TAG, "删除token成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "删除token失败");
			if (null != jedis) {
				 jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("删除token失败");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static void deleteRecord(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			jedis.del(key);
			Log.i(TAG, "删除record成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "删除record失败");
			if (null != jedis) {
				 jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("删除record失败");
		} finally {
			returnJedisResource(jedis);
		}
	}

	public static Map<String, String> getToken(String token){
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			Map<String, String> remap = jedis.hgetAll(token);
			Log.i(TAG, "获取token成功");
			return remap;
		} catch (Exception e) {
			Log.e(TAG, "获取token失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("获取token失败");
		} finally {
			returnJedisResource(jedis);
		}
	}
	
	public static String getRecord(String key){
		Jedis jedis = null;
		try {
			jedis = getJedisResource();
			String value = jedis.get(key);
			Log.i(TAG, "获取record成功");
			return value;
		} catch (Exception e) {
			Log.e(TAG, "获取record失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("获取record失败");
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
			Log.i(TAG, "延长token过期时间成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "延长token过期时间失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("延长token过期时间失败");
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
			Log.i(TAG, "验证token成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "验证token失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("验证token失败");
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
			Log.i(TAG, "验证record成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "验证record失败");
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new RuntimeException("验证record失败");
		} finally {
			returnJedisResource(jedis);
		}
		return bl;
	}
}
