package com.liqq.conf.security.kaptcha;

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
		String kaptchaKey = token.getKaptchaKey();
		String kaptchaValue = token.getKaptchaValue();
		String value = sysCacheService.getKey(Constant.KAPTCHA_CACHE_PREFIX + kaptchaKey);
		if (!kaptchaValue.equalsIgnoreCase(value)) {
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
		// 验证通过
		// 加载资源
		List<SysResource> sysResourceList = sysResourceMapper.selectByUserId(sysUser.getId());
		UsernamePasswordKaptchaToken result = new UsernamePasswordKaptchaToken(principal, credentials,sysResourceList);
		// 帐号信息的密码清除,然后放在Authentication
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
