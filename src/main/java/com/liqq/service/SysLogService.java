package com.liqq.service;

import com.liqq.model.SysLog;

public interface SysLogService {

	/**
	 * 插入日志
	 * @param sysLog
	 * @return
	 */
	int save(SysLog sysLog);

	
}
