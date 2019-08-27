package com.liqq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liqq.dao.mysql.SysLogMapper;
import com.liqq.model.SysLog;
import com.liqq.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public int save(SysLog sysLog) {
		return sysLogMapper.insert(sysLog);
	}
}
