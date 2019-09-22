package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liqq.common.Code;
import com.liqq.common.Constant;
import com.liqq.common.ReturnData;

/**
 * Anonymous或RememberMe的未授权处理，即未认证的非法访问
 * 
 * @author Administrator
 *
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// 返回授权拒绝信息
		ReturnData rd = new ReturnData(Code.ACCESS_ERROR, "未认证");
		String errorInfo = JSON.toJSONString(rd);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding(Constant.CHARSET_UTF8);
		response.getOutputStream().write(errorInfo.getBytes(Constant.CHARSET_UTF8));

	}

}
