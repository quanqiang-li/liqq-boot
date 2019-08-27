package com.liqq.conf;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 在需要异步执行的地方加上注解@Async("myTaskExecutor")
	 * @return
	 */
	@Bean("myTaskExecutor")
	public Executor taskExecutor() {
		// cpu数量
		int cpuNum = Runtime.getRuntime().availableProcessors();
		int threadNum = cpuNum <= 1 ? 2 : cpuNum;
		logger.info("可用cpu{}",cpuNum);
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 核心线程数,线程池创建时候初始化的线程数,保证至少一个线程
		executor.setCorePoolSize(threadNum / 2);
		// 线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
		executor.setMaxPoolSize(threadNum);
		// 用来缓冲执行任务的队列
		executor.setQueueCapacity(threadNum * 20);
		// 允许线程的空闲时间60秒
		executor.setKeepAliveSeconds(60);
		// 线程池名的前缀
		executor.setThreadNamePrefix("myTaskExecutor-");
		// 线程池对拒绝任务的处理策略,这里是终止,丢弃处理
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		return executor;
	}
}
