package com.liqq.service;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liqq.Application;
import com.liqq.model.SysLog;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestSysLogService {

	@Autowired
	private SysLogService sysLogService;
	
	@Test
	public void testSave() throws InterruptedException, ExecutionException{
		SysLog sysLog = new SysLog();
		sysLog.setArgs("");
		sysLog.setCreateTime(new Date());
		sysLog.setMethod("com.liqq.Application.main");
		sysLog.setModule("启动");
		sysLog.setRunTime(800L);
		sysLog.setStatus((byte)0);
		Future<Integer> save = sysLogService.save(sysLog);
		System.out.println("插入数据条数:" + save.get());
		System.out.println("主键:" + sysLog.getId());
	}
	
	
	
	
}
