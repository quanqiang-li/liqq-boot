package com.liqq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liqq.dao.mysql.SysDictMapper;
import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;
import com.liqq.service.SysDictService;

/**
 * 系统接口表接口实现
 * 
 * @author carl
 *
 */
@Service
public class SysDictServiceImpl implements SysDictService {

	@Autowired
	private SysDictMapper sysDictMapper;

	/**
	 * 插入不为null的属性
	 * 
	 * @param sysDict
	 */

	@Override
	public int save(SysDict sysDict) {
		return sysDictMapper.insertSelective(sysDict);
	}

	@Override
	public PageInfo<SysDict> selectPage(SysDictExample example, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SysDict> list = sysDictMapper.selectByExample(example);
		// 用PageInfo对结果进行包装
		PageInfo<SysDict> page = new PageInfo<>(list);
		return page;
	}

	/**
	 * 更新不为null的属性
	 * 
	 * @param record
	 *            更新属性
	 * @param example
	 *            更新条件
	 */
	@Transactional
	@Override
	public int update(SysDict record, SysDictExample example) {
		return sysDictMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 删除数据
	 * 
	 * @param example
	 * @return
	 */
	@Override
	public int delete(SysDictExample example) {
		return sysDictMapper.deleteByExample(example);
	}
}
