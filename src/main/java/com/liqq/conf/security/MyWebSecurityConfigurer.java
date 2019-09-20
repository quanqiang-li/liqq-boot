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
/**
 * Security的自定义配置
 * @author Administrator
 *
 */
@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsernamePasswordKaptchaProvider usernamePasswordKaptchaProvider;
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;
	@Autowired
	private MyAuthFailureHandler myAuthFailureHandler;
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	@Autowired
	private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

	@Bean
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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 配置认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordKaptchaProvider);
	}

	// 配置授权
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
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		System.out.println(encode);
		System.out.println(bCryptPasswordEncoder.matches("123456", encode));
	}

}
