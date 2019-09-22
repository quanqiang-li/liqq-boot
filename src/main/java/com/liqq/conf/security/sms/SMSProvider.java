package com.liqq.conf.security.sms;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class SMSProvider implements AuthenticationProvider {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private SysResourceMapper sysResourceMapper;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SMSToken token = (SMSToken) authentication;
		// 短信验证码比对
		String mobile = token.getPrincipal();
		String smsCode = token.getCredentials();
		String value = sysCacheService.getKey(Constant.SMS_PREFIX + mobile);
		if (!smsCode.equalsIgnoreCase(value)) {
			throw new BadCredentialsException("短信验证码错误");
		}
		// 从数据库查询用户 可以判断用户是否可用,密码过期等等...
		SysUserExample example = new SysUserExample();
		example.createCriteria().andMobileEqualTo(mobile);
		List<SysUser> selectByExample = sysUserMapper.selectByExample(example);
		if (selectByExample == null || selectByExample.isEmpty()) {
			logger.error("User '{}' not found", mobile);
			throw new UsernameNotFoundException("用户不存在");
		}
		// 验证通过 加载资源
		SysUser sysUser = selectByExample.get(0);
		List<SysResource> sysResourceList = sysResourceMapper.selectByUserId(sysUser.getId());
		SMSToken result = new SMSToken(mobile, smsCode, sysResourceList);
		// 帐号信息的密码清除,然后放在Authentication
		sysUser.setPassword(null);
		result.setDetails(sysUser);
		// 用户输入的密码清除
		result.eraseCredentials();
		return result;
	}
	@Override
	public boolean supports(Class<?> authentication) {
		return (SMSToken.class.isAssignableFrom(authentication));
	}

}
