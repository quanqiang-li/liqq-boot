package com.liqq.conf.security.sms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

/**
 * 参考 UsernamePasswordAuthenticationFilter
 * 
 * @author liqq
 *
 */
public class SMSFilter extends AbstractAuthenticationProcessingFilter {

	private String mobileParameter = "mobile";
	private String smsCodeParameter = "smsCode";

	@Autowired
	private SMSProvider smsProvider;

	public SMSFilter() {
		super(new AntPathRequestMatcher("/smsLogin"));
	}

	// 实际认证入口
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String mobile = request.getParameter(mobileParameter);
		if (StringUtils.isEmpty(mobile)) {
			throw new AuthenticationServiceException("请输入手机号");
		}
		String smsCode = request.getParameter(smsCodeParameter);
		if (StringUtils.isEmpty(smsCode)) {
			throw new AuthenticationServiceException("请输入短信验证码");
		}
		mobile = mobile.trim();
		SMSToken token = new SMSToken(mobile, smsCode);
		return smsProvider.authenticate(token);
	}

}
