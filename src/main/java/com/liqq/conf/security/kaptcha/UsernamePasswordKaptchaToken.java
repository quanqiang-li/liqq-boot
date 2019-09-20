package com.liqq.conf.security.kaptcha;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义token,参考UsernamePasswordAuthenticationToken
 * 
 * @author liqq
 *
 */
public class UsernamePasswordKaptchaToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	// ~ Instance fields
	// ================================================================================================

	private final String principal;
	private String credentials;
	// 验证码k
	private String kaptchaKey;
	// 验证码v
	private String kaptchaValue;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordKaptchaToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 */
	public UsernamePasswordKaptchaToken(String principal, String credentials, String kaptchaKey, String kaptchaValue) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.kaptchaKey = kaptchaKey;
		this.kaptchaValue = kaptchaValue;
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
	public UsernamePasswordKaptchaToken(String principal, String credentials,
			Collection<? extends GrantedAuthority> authorities) {
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

	public String getKaptchaKey() {
		return kaptchaKey;
	}

	public String getKaptchaValue() {
		return kaptchaValue;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

}
