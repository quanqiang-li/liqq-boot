package com.liqq.conf.security.sms;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义token,参考UsernamePasswordAuthenticationToken
 * 
 * @author liqq
 *
 */
public class SMSToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	// 手机号
	private final String principal;
	// 短信验证码
	private String credentials;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordKaptchaToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 */
	public SMSToken(String principal, String credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 *
	 * @param principal
	 * @param credentials
	 * @param kaptcha
	 * @param authorities
	 */
	public SMSToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}

	@Override
	public String getCredentials() {
		return this.credentials;
	}

	@Override
	public String getPrincipal() {
		return this.principal;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

}
