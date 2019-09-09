package com.liqq.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.liqq.dao.mysql.SysCacheMapper;
import com.liqq.model.SysCacheExample;

/**
 * 定时清理过期缓存
 * 
 * @author liqq
 *
 */
@Component
@EnableScheduling
public class ClearCacheTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysCacheMapper sysCacheMapper;

	/**
	 * 清理过期的缓存
	 */
	@Scheduled(cron = "${ClearCacheTask.cron}")
	public void clearCache() {
		logger.info("开始清理过期缓存...");
		SysCacheExample example = new SysCacheExample();
		example.createCriteria().andExpireTimeLessThan(new Date());
		int delete = sysCacheMapper.deleteByExample(example);
		logger.info("清理过期缓存结束,总共清理数据{}条", delete);
	}
}
