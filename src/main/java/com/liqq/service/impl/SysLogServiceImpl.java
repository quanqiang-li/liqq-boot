package com.liqq.service.impl;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.liqq.dao.mysql.SysLogMapper;
import com.liqq.model.SysLog;
import com.liqq.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysLogMapper sysLogMapper;

	// 注意返回值只能是void或者Future,调用者通过get获取结果
	@Override
	@Async("myTaskExecutor")
	public Future<Integer> save(SysLog sysLog) {
		int insert = 0;
		try {
			insert = sysLogMapper.insert(sysLog);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new AsyncResult<Integer>(insert);
	}
}
