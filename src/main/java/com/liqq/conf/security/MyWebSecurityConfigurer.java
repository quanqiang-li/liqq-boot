package com.liqq.conf.security;

import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 配置认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("guest").password(passwordEncoder().encode("guest")).roles("guest");
		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root")).roles("root");
	}

	// 配置授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/api/guest").hasRole("guest")
		.antMatchers("/api/root").hasRole("root")
		.antMatchers("/api/authed").authenticated()
		.anyRequest().permitAll()
		.and().httpBasic();
		//.and().formLogin().usernameParameter("uname").passwordParameter("pword");
	}
	
	public static void main(String[] args) {
		System.out.println(Base64.getEncoder().encodeToString("root:root".getBytes()));
		System.out.println(Base64.getEncoder().encodeToString("guest:guest".getBytes()));
	}

}
