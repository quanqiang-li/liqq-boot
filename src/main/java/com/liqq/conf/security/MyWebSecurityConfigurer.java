package com.liqq.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.liqq.conf.security.kaptcha.UsernamePasswordKaptchaFilter;
import com.liqq.conf.security.kaptcha.UsernamePasswordKaptchaProvider;

@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsernamePasswordKaptchaProvider usernamePasswordKaptchaProvider;
	@Autowired
	private MyAuthSuccessHandler myAuthSuccessHandler;
	@Autowired
	private MyAuthFailureHandler myAuthFailureHandler;

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
		http.authorizeRequests().antMatchers("/", "/index.html", "/favicon.ico", "/css/**", "/error/**", "/html/**", "/js/**", "/kaptchaLogin", "/logout", "/kaptcha/**")
				.permitAll();
		//http.logout().
		http.sessionManagement().disable();
		// 配置url 授权访问
		http.authorizeRequests().anyRequest().access("@myAccessDecisionManager.decide()");
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
