package com.liqq.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.liqq.Application;
import com.liqq.model.SysDict;
import com.liqq.model.SysDictExample;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestSysDictService {

	@Autowired
	private SysDictService sysDictService;
	
	@Test
	public void testSave(){
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
	
	@Test
	public void testSelect(){
		SysDictExample example = new SysDictExample();
		example.createCriteria().andCodeEqualTo("sex");
		int pageNum = 1;//第1页
		int pageSize = 1;//每页1条
		PageInfo<SysDict> page = sysDictService.selectPage(example,pageNum,pageSize);
		//测试PageInfo全部属性
		//PageInfo包含了非常全面的分页属性
		
		assertEquals(1, page.getPageNum());
		assertEquals(1, page.getPageSize());
		assertEquals(1, page.getStartRow());
		assertEquals(1, page.getEndRow());
		assertEquals(2, page.getTotal());
		assertEquals(2, page.getPages());
		assertEquals(true, page.isIsFirstPage());
		assertEquals(false, page.isIsLastPage());
		assertEquals(false, page.isHasPreviousPage());
		assertEquals(true, page.isHasNextPage());
		//获取数据
		page.getList().forEach(item -> System.out.println(item.getK() + "=" + item.getV()));
	}
	
	
}
