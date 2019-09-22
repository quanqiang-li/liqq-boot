package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liqq.common.Code;
import com.liqq.common.Constant;
import com.liqq.common.ReturnData;
/**
 * 认证失败
 * @author liqq
 *
 */
@Component
public class MyAuthFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		// 返回认证失败信息
		ReturnData rd = new ReturnData(Code.AUTH_ERROR, exception.getMessage());
		String errorInfo = JSON.toJSONString(rd);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding(Constant.CHARSET_UTF8);
		response.getOutputStream().write(errorInfo.getBytes(Constant.CHARSET_UTF8));
		
	}

}
