package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UsernamePasswordKaptchaFilter extends AbstractAuthenticationProcessingFilter {

	private String usernameParameter = "username";
	private String passwordParameter = "password";
	private String kaptchaParameter = "kaptcha";
	
	@Autowired
	private UsernamePasswordKaptchaProvider UsernamePasswordKaptchaProvider;
	

	public UsernamePasswordKaptchaFilter() {
		super(new AntPathRequestMatcher("/kaptchaLogin","POST"));
	}
	// 实际验证入口
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String principal = request.getParameter(usernameParameter);
		if (StringUtils.isEmpty(principal)) {
			return null;
		}
		String credentials = request.getParameter(passwordParameter);
		if (StringUtils.isEmpty(credentials)) {
			return null;
		}
		String kaptcha = request.getParameter(kaptchaParameter);
		if (StringUtils.isEmpty(kaptcha)) {
			return null;
		}
		principal = principal.trim();
		UsernamePasswordKaptchaToken token = new UsernamePasswordKaptchaToken(principal, credentials, kaptcha);
		// Allow subclasses to set the "details" property
		setDetails(request, token);
		return UsernamePasswordKaptchaProvider.authenticate(token);
	}

	
	protected void setDetails(HttpServletRequest request, UsernamePasswordKaptchaToken token) {
		token.setDetails(authenticationDetailsSource.buildDetails(request));
	}

}