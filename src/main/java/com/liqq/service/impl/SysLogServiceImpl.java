package com.liqq.service.impl;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.liqq.dao.mysql.SysLogMapper;
import com.liqq.model.SysLog;
import com.liqq.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	// 注意返回值只能是void或者Future,调用者通过get获取结果
	@Override
	@Async("myTaskExecutor")
	public Future<Integer> save(SysLog sysLog) {
		int insert = sysLogMapper.insert(sysLog);
		return new AsyncResult<Integer>(insert);
	}
}
