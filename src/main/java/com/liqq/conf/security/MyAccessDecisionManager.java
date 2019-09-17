package com.liqq.conf.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * 授权处理
 * @author liqq
 *
 */
@Component
public class MyAccessDecisionManager {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * 验证是否授权访问
	 * @param request
	 * @param authentication
	 * @return
	 */
	// 必须是public 否则spel报错el1004e method call method cannot be found on type
	public boolean decide() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
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
}
