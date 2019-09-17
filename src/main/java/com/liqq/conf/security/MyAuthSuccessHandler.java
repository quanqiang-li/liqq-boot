package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liqq.common.Code;
import com.liqq.common.Constant;
import com.liqq.common.ReturnData;
import com.liqq.service.SysCacheService;

/**
 * 认证成功
 * @author liqq
 *
 */
@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SysCacheService sysCacheService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 缓存登陆后的用户信息,并返回给调用者
		ReturnData rd = new ReturnData(Code.OK, authentication);
		String loginInfo = JSON.toJSONString(rd);
		String id = request.getSession().getId();
		sysCacheService.writeValue(Constant.LOGIN_CACHE_PREFIX + id, loginInfo, null);
		response.setCharacterEncoding(Constant.CHARSET_UTF8);
		response.getOutputStream().write(loginInfo.getBytes(Constant.CHARSET_UTF8));
	}

}
