package com.liqq.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.liqq.conf.security.kaptcha.UsernamePasswordKaptchaFilter;
import com.liqq.conf.security.kaptcha.UsernamePasswordKaptchaProvider;
import com.liqq.conf.security.sms.SMSFilter;
import com.liqq.conf.security.sms.SMSProvider;
/**
 * Security的自定义配置
 * @author Administrator
 *
 */
@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsernamePasswordKaptchaProvider usernamePasswordKaptchaProvider;//图形验证码的认证逻辑
	@Autowired
	private SMSProvider smsProvider;//短信验证码的认证逻辑
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;//认证成功处理
	@Autowired
	private MyAuthFailureHandler myAuthFailureHandler;//认证失败处理
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;//授权逻辑
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;//授权失败处理，前提认证成功
	@Autowired
	private MyAuthenticationEntryPoint myAuthenticationEntryPoint;//授权失败处理，前提未认证

	@Bean // 自定义filter
	public UsernamePasswordKaptchaFilter usernamePasswordKaptchaFilter() {
		UsernamePasswordKaptchaFilter filter = new UsernamePasswordKaptchaFilter();
		try {
			filter.setAuthenticationManager(authenticationManager());
			filter.setAuthenticationSuccessHandler(myAuthSuccessHandler);
			filter.setAuthenticationFailureHandler(myAuthFailureHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filter;
	}
	@Bean // 自定义filter
	public SMSFilter smsFilter() {
		SMSFilter filter = new SMSFilter();
		try {
			filter.setAuthenticationManager(authenticationManager());
			filter.setAuthenticationSuccessHandler(myAuthSuccessHandler);
			filter.setAuthenticationFailureHandler(myAuthFailureHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 配置认证，支持多个
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordKaptchaProvider);
		auth.authenticationProvider(smsProvider);
	}

	// 配置授权，完全自定义授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 配置url 授权访问
		http.authorizeRequests().anyRequest().authenticated().accessDecisionManager(myAccessDecisionManager);
		http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler).authenticationEntryPoint(myAuthenticationEntryPoint);
		// 默认logout退出会重定向到/login?logout,这里使用返回状态,适用restfult场景
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		// 不使用session保存Authentication
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// security的CsrfFilter跨站请求伪造,默认只允许"GET", "HEAD", "TRACE",
		// "OPTIONS",不支持POST,这里粗暴禁用
		http.csrf().disable();
		http.addFilterBefore(usernamePasswordKaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(smsFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		System.out.println(encode);
		System.out.println(bCryptPasswordEncoder.matches("123456", encode));
	}

}
