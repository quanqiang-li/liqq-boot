package com.liqq.service;

import java.util.List;

import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;

/**
 * 系统字典表接口
 * @author carl
 *
 */
public interface SysDictService {

	/**
	 * 插入不为空的数据
	 * @param sysDict
	 * @return
	 */
	int save(SysDict sysDict);

	/**
	 * 查询列表
	 * @param example
	 * @return
	 */
	List<SysDict> select(SysDictExample example);

}
