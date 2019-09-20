package com.liqq.conf.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.liqq.common.Constant;
import com.liqq.service.SysCacheService;

/**
 * 授权处理
 * 
 * @author liqq
 *
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

	@Autowired
	private SysCacheService sysCacheService;

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
		// SessionCreationPolicy.STATELESS 禁用session来保存authentication，这里从缓存取出来
		String token = extractToken(request);
		if(StringUtils.isEmpty(token)) {
			throw new AccessDeniedException("未认证");
		}
		String value = sysCacheService.getKey(Constant.LOGIN_CACHE_PREFIX + token);
		if(StringUtils.isEmpty(value)) {
			throw new AccessDeniedException("非法访问");
		}
		JSONArray authorities = JSON.parseObject(value).getJSONArray("authorities");
		if(authorities == null || authorities.isEmpty()) {
			throw new AccessDeniedException("未授权访问");
		}
		for (int i = 0; i < authorities.size(); i++) {
			String href = authorities.getJSONObject(i).getString("href");
			if (antPathMatcher.match(href, request.getRequestURI())) {
				return;
			}
		}
		throw new AccessDeniedException("未授权访问");
	}

	/**
	 * 提取token的值
	 * 
	 * @param request
	 * @return
	 */
	private String extractToken(HttpServletRequest request) {
		String token = request.getHeader(Constant.TOKEN_PARAM);
		if (StringUtils.isEmpty(token)) {
			token = request.getParameter(Constant.TOKEN_PARAM);
		}
		return token;

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
