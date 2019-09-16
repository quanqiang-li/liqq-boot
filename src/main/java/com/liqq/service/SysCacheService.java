package com.liqq.service;

public interface SysCacheService {
	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param validTime
	 *            有效时间,单位毫秒 null永不过期
	 * @return 插入条数
	 */
	int writeValue(String key, String value, Integer validTime);

	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	int deleteKey(String key);
	
	/**
	 * 获取key的值
	 * @param key
	 * @return
	 */
	String getKey(String key);

}
