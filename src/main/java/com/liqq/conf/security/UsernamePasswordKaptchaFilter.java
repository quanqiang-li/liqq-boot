package com.liqq.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public UsernamePasswordKaptchaFilter(String pattern, String httpMethod) {
		super(new AntPathRequestMatcher(pattern, httpMethod));
	}

	// 实际验证入口
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String principal = request.getParameter(usernameParameter);
		if (StringUtils.isEmpty(principal)) {
			principal = "";
		}
		String credentials = request.getParameter(passwordParameter);
		if (StringUtils.isEmpty(credentials)) {
			credentials = "";
		}
		String kaptcha = request.getParameter(kaptchaParameter);
		if (StringUtils.isEmpty(kaptcha)) {
			kaptcha = "";
		}
		principal = principal.trim();
		UsernamePasswordKaptchaToken token = new UsernamePasswordKaptchaToken(principal, credentials, kaptcha);
		// Allow subclasses to set the "details" property
		//setDetails(request, token);
		return this.getAuthenticationManager().authenticate(token);
	}

	
//	protected void setDetails(HttpServletRequest request, UsernamePasswordKaptchaToken token) {
//		token.setDetails(authenticationDetailsSource.buildDetails(request));
//	}

}
