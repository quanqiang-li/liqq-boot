package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liqq.common.Code;
import com.liqq.common.Constant;
import com.liqq.common.ReturnData;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 返回授权拒绝信息
		ReturnData rd = new ReturnData(Code.ACCESS_ERROR, accessDeniedException.getMessage());
		String errorInfo = JSON.toJSONString(rd);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding(Constant.CHARSET_UTF8);
		response.getOutputStream().write(errorInfo.getBytes(Constant.CHARSET_UTF8));
	}

}
