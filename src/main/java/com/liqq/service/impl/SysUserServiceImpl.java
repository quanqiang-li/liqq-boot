package com.liqq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liqq.common.Constant;
import com.liqq.dao.mysql.SysUserMapper;
import com.liqq.model.SysUser;
import com.liqq.model.SysUserExample;
import com.liqq.model.SysUserExample.Criteria;
import com.liqq.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public PageInfo<SysUser> selectPage(SysUser sysUser, Integer pageNum, Integer pageSize) {
		SysUserExample sysUserExample = new SysUserExample();
		Criteria createCriteria = sysUserExample.createCriteria();
		if (!StringUtils.isEmpty(sysUser.getMobile())) {
			createCriteria.andMobileEqualTo(sysUser.getMobile());
		}
		// 不传参数，默认第一页 10条
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : Constant.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize);
		List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
		// 用PageInfo对结果进行包装
		PageInfo<SysUser> page = new PageInfo<>(list);
		return page;
	}
}
