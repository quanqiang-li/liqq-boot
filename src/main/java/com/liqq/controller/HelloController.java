package com.liqq.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

	// 无参 http://localhost:8080/hello
	@GetMapping()
	public String hello() {
		return "hello world";
	}

	// 有参 http://localhost:8080/hello/zhangsan
	@GetMapping("{name}")
	public String name(@PathVariable String name) {
		return "hello " + name;
	}

	// 日期参数添加注解,如果是对象则加属性上,全局设置可在配置文件处理
	//spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
	//spring.jackson.time-zone=GMT+8
	@GetMapping("age")
	public String age(Integer age, @DateTimeFormat(pattern="yyyy-MM-dd") Date birth) {
		return "birth " + birth + "and age" + age;
	}

}
