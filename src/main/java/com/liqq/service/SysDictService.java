package com.liqq.service;

import com.github.pagehelper.PageInfo;
import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;

/**
 * 系统字典表接口
 * 
 * @author carl
 *
 */
public interface SysDictService {

	/**
	 * 插入不为空的数据
	 * 
	 * @param sysDict
	 * @return
	 */
	int save(SysDict sysDict);

	/**
	 * 查询列表,分页
	 * @param example
	 * @param pageNum 从1开始计数
	 * @param pageSize
	 * @return
	 */
	PageInfo<SysDict> selectPage(SysDictExample example,int pageNum, int pageSize);

	/**
	 * 更新不为null的属性
	 * 
	 * @param record
	 *            更新属性
	 * @param example
	 *            更新条件
	 * @return
	 */
	int update(SysDict record, SysDictExample example);

	/**
	 * 删除数据
	 * @param example
	 * @return
	 */
	int delete(SysDictExample example);
	
	

}
