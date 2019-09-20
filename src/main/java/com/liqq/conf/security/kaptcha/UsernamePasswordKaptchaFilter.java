package com.liqq.conf.security.kaptcha;

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
public class UsernamePasswordKaptchaFilter extends AbstractAuthenticationProcessingFilter {

	private String usernameParameter = "username";
	private String passwordParameter = "password";
	private String kaptchaParameter = "kaptcha";

	@Autowired
	private UsernamePasswordKaptchaProvider UsernamePasswordKaptchaProvider;

	public UsernamePasswordKaptchaFilter() {
		super(new AntPathRequestMatcher("/kaptchaLogin"));
	}

	// 实际认证入口
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO 放在另一个filter中 https://golb.hplar.ch/2019/05/stateless.html
		// 从request header或者parameter获取token（登录成功生成的token返回给客户端）
		// 如果有token，则从缓存获取登录用户信息，获取之后反序列化Authentication 直接返回，获取不到的执行下面的正常认证
		// 如果没有token，则执行下面的正常认证

		String principal = request.getParameter(usernameParameter);
		if (StringUtils.isEmpty(principal)) {
			throw new AuthenticationServiceException("请输入用户名");
		}
		String credentials = request.getParameter(passwordParameter);
		if (StringUtils.isEmpty(credentials)) {
			throw new AuthenticationServiceException("请输入密码");
		}
		String kaptcha = request.getParameter(kaptchaParameter);
		if (StringUtils.isEmpty(kaptcha)) {
			throw new AuthenticationServiceException("请输入验证码");
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
