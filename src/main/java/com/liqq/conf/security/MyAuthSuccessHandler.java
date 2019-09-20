package com.liqq.conf.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	// 缓存登陆后的用户信息,并返回token给调用者
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String token = UUID.randomUUID().toString();
		String authStr = JSON.toJSONString(authentication);
		// 缓存token一天
		sysCacheService.writeValue(Constant.LOGIN_CACHE_PREFIX + token, authStr, 24*60*60*1000);
		// 返回token给调用者
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.TOKEN_PARAM, token);
		ReturnData rd = new ReturnData(Code.OK, data);
		response.setCharacterEncoding(Constant.CHARSET_UTF8);
		response.getOutputStream().write(JSON.toJSONString(rd).getBytes(Constant.CHARSET_UTF8));
	}

}
