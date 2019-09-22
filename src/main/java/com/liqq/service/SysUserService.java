package com.liqq.service;

import com.github.pagehelper.PageInfo;
import com.liqq.model.SysUser;

public interface SysUserService {

	/**
	 * 分页查询用户列表，支持条件查询
	 * @param sysUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<SysUser> selectPage(SysUser sysUser,Integer pageNum, Integer pageSize);
}
