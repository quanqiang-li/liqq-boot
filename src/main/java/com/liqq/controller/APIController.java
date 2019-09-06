package com.liqq.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class APIController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("noAuth")
	public String noAuth() {
		return "noAuth";
	}

	@GetMapping("authed")
	public String authed(HttpServletRequest req) {
		Enumeration<String> headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = (String) headerNames.nextElement();
			String value = req.getHeader(name);
			logger.info("{}={}",name,value);
		}
		return "authed";
	}

	@GetMapping("guest")
	public String guest() {
		return "guest";
	}
	@GetMapping("root")
	public String root() {
		return "root";
	}
}
