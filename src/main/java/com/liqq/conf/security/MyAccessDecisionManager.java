package com.liqq.conf.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 授权处理
 * 
 * @author liqq
 *
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	// 放行的资源
	private static String[] permitAll = { "/", "/index.html", "/favicon.ico", "/css/**", "/error/**", "/html/**",
			"/js/**", "/kaptchaLogin", "/logout", "/kaptcha/**" };

	/**
	 * 授权失败抛出异常
	 * 
	 * @param authentication   认证成功的对象
	 * @param object           请求的资源
	 * @param configAttributes 资源的属性
	 * @throws AccessDeniedException
	 * @throws InsufficientAuthenticationException
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// 获取请求的资源
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		// 直接放行
		for (int i = 0; i < permitAll.length; i++) {
			if (antPathMatcher.match(permitAll[i], request.getRequestURI())) {
				return;
			}
		}
		// 需要认证授权才可以访问
		if (authentication == null) {
			throw new AccessDeniedException("未认证");
		}
		// 这里是使用SecurityContextHolder,也可以缓存到redis
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		while (iterator.hasNext()) {
			String authority = iterator.next().getAuthority();
			if (antPathMatcher.match(authority, request.getRequestURI())) {
				return;
			}
		}
		throw new AccessDeniedException("未授权访问");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
