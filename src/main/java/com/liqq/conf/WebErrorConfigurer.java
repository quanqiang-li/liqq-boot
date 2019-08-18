package com.liqq.conf;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Component
public class WebErrorConfigurer extends BasicErrorController {

	@Autowired
	public WebErrorConfigurer(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
		// 必须使用serverProperties.getError()来获取errorProperties,不能直接注入errorProperties
		super(errorAttributes, serverProperties.getError());
	}

	// 处理异常返回json
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String errorJson(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request,
				isIncludeStackTrace(request, MediaType.APPLICATION_JSON));
		HttpStatus status = getStatus(request);
		body.put("status", status);
		body.put("addr", request.getRemoteAddr());
		return JSON.toJSONString(body);
	}
	
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		
		return new ModelAndView("/error/error.html");
	}

}
