package com.liqq.conf.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * 验证是否授权访问
	 * 
	 * @param request
	 * @param authentication
	 * @return
	 */
	// 必须是public 否则spel报错el1004e method call method cannot be found on type
	public boolean decide() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		// 这里是使用SecurityContextHolder,也可以缓存到redis
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		while (iterator.hasNext()) {
			String authority = iterator.next().getAuthority();
			if (antPathMatcher.match(authority, request.getRequestURI())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 授权失败抛出异常
	 * @param authentication  认证成功的对象
	 * @param object  请求的资源
	 * @param configAttributes 资源的属性
	 * @throws AccessDeniedException
	 * @throws InsufficientAuthenticationException
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(authentication == null) {
			throw new AccessDeniedException("未认证");
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
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
		logger.info("自定义授权逻辑支持ConfigAttribute={}", attribute.toString());
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("自定义授权逻辑支持Class={}", clazz.toString());
		return true;
	}
}
