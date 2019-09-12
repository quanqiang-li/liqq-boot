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

@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsernamePasswordKaptchaProvider UsernamePasswordKaptchaProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 配置认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(UsernamePasswordKaptchaProvider);
	}

	// 配置授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/api/guest").hasRole("guest")
//		.antMatchers("/api/admin").hasRole("admin")
//		.antMatchers("/api/authed").authenticated()
//		.anyRequest().permitAll()
//		.and().httpBasic();
		//.and().formLogin().usernameParameter("uname").passwordParameter("pword");
		http.addFilterBefore(new UsernamePasswordKaptchaFilter("/login","POST"), UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().anyRequest().authenticated();
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		System.out.println(encode);
		System.out.println(bCryptPasswordEncoder.matches("123456", encode));
	}

}
