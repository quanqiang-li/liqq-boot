package com.liqq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liqq.dao.mysql.SysDictMapper;
import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;
import com.liqq.service.SysDictService;
/**
 * 系统接口表接口实现
 * @author carl
 *
 */
@Service
public class SysDictServiceImpl implements SysDictService{

	@Autowired
	private SysDictMapper sysDictMapper;
	
	/**
	 * 插入不为null的字段
	 * @param sysDict
	 */
	@Override
	public int save(SysDict sysDict){
		return sysDictMapper.insertSelective(sysDict);
	}
	
	@Override
	public List<SysDict> select(SysDictExample example){
		return sysDictMapper.selectByExample(example);
	}
}
