package com.liqq.service;

import java.util.concurrent.Future;

import com.liqq.model.SysLog;

public interface SysLogService {

	/**
	 * 插入日志
	 * @param sysLog
	 * @return
	 */
	Future<Integer> save(SysLog sysLog);

	
}
