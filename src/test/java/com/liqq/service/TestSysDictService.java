package com.liqq.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liqq.Application;
import com.liqq.model.SysDict;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestSysDictService {

	@Autowired
	private SysDictService sysDictService;
	
	@Test
	public void test(){
		SysDict sysDict = new SysDict();
		sysDict.setCode("auth");
		sysDict.setName("认证类型");
		sysDict.setK("0");
		sysDict.setV("身份证");
		sysDict.setCreateTime(new Date());
		int save = sysDictService.save(sysDict);
		System.out.println("插入数据条数:" + save);
		System.out.println("主键:" + sysDict.getId());
	}
}
