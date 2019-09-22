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
import com.liqq.common.Constant;
import com.liqq.service.SysCacheService;
import com.liqq.util.WebUtil;

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
	private static String[] permitAll = { "/", "/index.html", "/favicon.ico", "*.css", "/error/**", "/html/**",
			"*.js", "*.png", "/kaptchaLogin", "/logout", "/kaptcha/**", "/smsLogin", "/sms/**" , 
			"/v2/api-docs", "/swagger-ui.html", "/webjars/**","/swagger-resources/**" };

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
		String token = WebUtil.extractParam(request, Constant.TOKEN_PARAM);
		if (StringUtils.isEmpty(token)) {
			throw new AccessDeniedException("未认证");
		}
		String value = sysCacheService.getKey(Constant.LOGIN_CACHE_PREFIX + token);
		if (StringUtils.isEmpty(value)) {
			throw new AccessDeniedException("非法访问");
		}
		MyLoginInfo myLoginInfo = JSON.parseObject(value, MyLoginInfo.class);
		if (myLoginInfo.sysResourceList == null || myLoginInfo.sysResourceList.isEmpty()) {
			throw new AccessDeniedException("未授权访问");
		}
		for (int i = 0; i < myLoginInfo.sysResourceList.size(); i++) {
			String authority = myLoginInfo.sysResourceList.get(i).getAuthority();
			if (antPathMatcher.match(authority, request.getRequestURI())) {
				// 授权通过，放入userId，方便后续的记录日志等使用
				request.setAttribute(Constant.USER_ID, myLoginInfo.sysUser.getId());
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
