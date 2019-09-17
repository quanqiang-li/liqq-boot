package com.liqq.conf.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liqq.common.Constant;
import com.liqq.dao.mysql.SysResourceMapper;
import com.liqq.dao.mysql.SysUserMapper;
import com.liqq.model.SysResource;
import com.liqq.model.SysUser;
import com.liqq.model.SysUserExample;
import com.liqq.service.SysCacheService;

/**
 * 自定义provider,参考 AbstractUserDetailsAuthenticationProvider
 * 
 * @author liqq
 *
 */
@Component
public class UsernamePasswordKaptchaProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordKaptchaToken token = (UsernamePasswordKaptchaToken) authentication;
		// 验证码比对
		String kaptcha = token.getKaptcha();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String sessionId = servletRequestAttributes.getSessionId();
		String value = sysCacheService.getKey(Constant.KAPTCHA_CACHE_PREFIX + sessionId);
		if (!kaptcha.equalsIgnoreCase(value)) {
			throw new BadCredentialsException("验证码错误");
		}
		// 从数据库查询用户 可以判断用户是否可用,密码过期等等...
		String principal = token.getPrincipal();
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUsernameEqualTo(principal);
		List<SysUser> selectByExample = sysUserMapper.selectByExample(example);
		if (selectByExample == null || selectByExample.isEmpty()) {
			logger.error("User '{}' not found", principal);
			throw new UsernameNotFoundException("用户不存在");
		}
		// 比对密码
		String credentials = token.getCredentials();
		SysUser sysUser = selectByExample.get(0);
		String password = sysUser.getPassword();
		if(!passwordEncoder.matches(credentials, password)){
			throw new BadCredentialsException("密码错误");
		}
		// 验证通过,创建token,并返回
		// 加载资源
		List<SysResource> sysResourceList = sysResourceMapper.selectByUserId(sysUser.getId());
		//Collection<? extends GrantedAuthority> authorities = sysResourceList;
		UsernamePasswordKaptchaToken result = new UsernamePasswordKaptchaToken(principal, credentials, kaptcha, sysResourceList);
		// 帐号信息的密码清除
		sysUser.setPassword(null);
		result.setDetails(sysUser);
		// 用户输入的密码清除
		result.eraseCredentials();
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordKaptchaToken.class.isAssignableFrom(authentication));
	}

}
