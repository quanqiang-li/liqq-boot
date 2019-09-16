package com.liqq.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liqq.dao.mysql.SysCacheMapper;
import com.liqq.model.SysCache;
import com.liqq.model.SysCacheExample;
import com.liqq.service.SysCacheService;

@Service
public class SysCacheServiceImpl implements SysCacheService {

	@Autowired
	private SysCacheMapper sysCacheMapper;

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param validTime
	 *            有效时间,单位毫秒 null永不过期
	 * @return 插入条数
	 */
	@Override
	public int writeValue(String key, String value, Integer validTime) {
		SysCache record = new SysCache();
		Calendar instance = Calendar.getInstance();
		record.setK(key);
		record.setV(value);
		// 创建时间
		record.setCreateTime(instance.getTime());
		// 过期时间
		if (validTime != null) {
			instance.add(Calendar.MILLISECOND, validTime);
			record.setExpireTime(instance.getTime());
		}
		return sysCacheMapper.insert(record);
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public int deleteKey(String key) {
		SysCacheExample example = new SysCacheExample();
		example.createCriteria().andKEqualTo(key);
		return sysCacheMapper.deleteByExample(example);
	}

	/**
	 * 获取key的值
	 */
	@Override
	public String getKey(String key) {
		SysCacheExample example = new SysCacheExample();
		example.createCriteria().andKEqualTo(key);
		List<SysCache> selectByExample = sysCacheMapper.selectByExampleWithBLOBs(example);
		if (selectByExample == null || selectByExample.isEmpty()) {
			return null;
		}
		return selectByExample.get(0).getV();
	}
}
